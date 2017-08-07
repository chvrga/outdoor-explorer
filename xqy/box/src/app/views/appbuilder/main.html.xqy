(:
Copyright 2012-2015 MarkLogic Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
:)
xquery version "1.0-ml";

import module namespace c = "http://marklogic.com/roxy/config" at "/app/config/config.xqy";

import module namespace vh = "http://marklogic.com/roxy/view-helper" at "/roxy/lib/view-helper.xqy";

import module namespace facet = "http://marklogic.com/roxy/facet-lib" at "/app/views/helpers/facet-lib.xqy";

declare namespace search = "http://marklogic.com/appservices/search";

declare namespace ex = "http://chvrga.org/explore";
declare namespace ls = "http://chvrga.org/list";
declare namespace us = "http://chvrga.org/user";

declare option xdmp:mapping "false";

declare variable $q as xs:string? := vh:get("q");
declare variable $page as xs:int := vh:get("page");
declare variable $search-options as element(search:options) := vh:get("search-options");
declare variable $response as element(search:response)? := vh:get("response");
declare variable $sidebar_response as element(search:response)? := vh:get("sidebar_response");

declare function local:transform-snippet($nodes as node()*)
{
  for $n in $nodes
  return
    typeswitch($n)
      case element(search:highlight) return
        <span xmlns="http://www.w3.org/1999/xhtml" class="highlight">{fn:data($n)}</span>
      case element() return
        element div
        {
          attribute class { fn:local-name($n) },
          local:transform-snippet(($n/@*, $n/node()))
        }
      default return $n
};

vh:add-value("sidebar",

<div class="col-xs-3">
  {
    facet:facets($sidebar_response/search:facet, $q, $c:SIDEBAR-OPTIONS, $c:LABELS)
  }
</div>
),

let $page := ($response/@start - 1) div $c:DEFAULT-PAGE-LENGTH + 1
let $total-pages := fn:ceiling($response/@total div $c:DEFAULT-PAGE-LENGTH)
return
  <div xmlns="http://www.w3.org/1999/xhtml" id="search">
  {
    if ($response/@total gt 0) then
    (
      <div class="pagination">
        <span class="status">Showing {fn:string($response/@start)} to {fn:string(fn:min(($response/@start + $response/@page-length - 1, $response/@total)))} of <span id="total-results">{fn:string($response/@total)}</span> Results </span>
        <span class="nav">
          <span id="first" class="button">
          {
            if ($page gt 1) then
              <a href="/?q={$q}&amp;page=1">First</a>
            else
              "First"
          }
          </span>
          <span id="previous" class="button">
          {
            if ($page gt 1) then
              <a href="?q={$q}&amp;page={$page - 1}">&lt;</a>
            else
              "&lt;"
          }
          </span>
          <span id="next" class="button">
          {
            if ($page lt $total-pages) then
              <a href="?q={$q}&amp;page={$page + 1}">&gt;</a>
            else
              "&gt;"
          }
          </span>
          <span id="last" class="button">
          {
            if ($page lt $total-pages) then
              <a href="?q={$q}&amp;page={$total-pages}">Last</a>
            else
              "Last"
          }
          </span>
        </span>
      </div>,
      <div class="bottom50 destination-div">
      <div class="clearfix row" style="margin: 1% 0;"></div>

      {
        for $result at $i in $response/search:result
        let $doc := fn:doc($result/@uri)/*
        return
          <div class="result">
          {
            if (fn:matches($result/@uri/string(),"/explore.*")) then 
              (
                <div class="col-md-3 box" style="background-image: url('/image?uri={xdmp:url-encode($doc/ex:background/string())}')">
                <div class="box-in">
                  
                  {
                  (:<div style="">
                    <img src="/image/?uri={xdmp:url-encode($doc/ex:background/string())}"/>
                  </div>:)
                  }

                  <div style="">
                    <h4><a href="/details?uri={$result/@uri}">{$doc/ex:name/string()}</a></h4>
                  </div>
                  {
                  (:<div style="">
                    <span>{local:transform-snippet($result/search:snippet)}</span>
                  </div>:)
                  }
                </div>
                </div>
              )
              else ""
              
              
          }
          </div>
      }
      </div>
    )
    else
      <div class="results">
        <h2>No Results Found</h2>
      </div>
  }

  </div>