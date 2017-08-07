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

import module namespace vh = "http://marklogic.com/roxy/view-helper" at "/roxy/lib/view-helper.xqy";

import module namespace uv = "http://www.marklogic.com/roxy/user-view" at "/app/views/helpers/user-lib.xqy";

declare variable $view as item()* := vh:get("view");
declare variable $sidebar as item()* := vh:get("sidebar");

declare variable $title as xs:string? := "Box"; (:vh:get("title");:)
declare variable $appname := "Outdoor Explorer"
;
declare variable $username as xs:string? := vh:get("username");
declare variable $q as xs:string? := vh:get("q");

'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">',
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>{$title}</title>
    <link href="/css/main.css" type="text/css" rel="stylesheet"/>
    <link href="/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script src="/js/lib/less-1.3.0.min.js" type='text/javascript'></script>
    <script src="/js/lib/jquery-1.7.1.min.js" type='text/javascript'></script>
    <script src="/js/lib/jquery-ui-1.8.18.min.js" type='text/javascript'></script>
    <script src="/js/two-column.js" type='text/javascript'></script>
    <script src="/js/app.js" type='text/javascript'></script>
    { vh:get("additional-js") }
  </head>
  <body>
    <header id="headContainer">
      <nav class="navbar navbar-default">
        <div class="container-fluid">
          <div class="navbar-header">
          {
                if ($username) then
                    <a class="navbar-brand" href="/">{$appname}&nbsp;<i>{$username}</i></a>                  
                else
                    <a class="navbar-brand" href="/">{$appname}</a>
          }
          </div>

          <ul class="nav navbar-nav navbar-right">
            <li><a href="/"><i class="fa fa-home"></i>Home</a></li>
            <li><a href="/users" data-bind="click: showUsers"><i class="fa fa-comment"></i>Users</a></li> 
            {
            if ($username) then
                <li><a href="/newdestination" data-bind="click: addNewDestination"><i class="fa fa-shield"></i>New Destination</a></li>
            else ()
            }
            {
              uv:build-user($username, fn:concat("/user/profile?user=", $username), "/user/login", "/user/register", "/user/logout")
            }

          </ul>

        </div>
      </nav>
    </header>

    

    <div class="top container-fluid">
      <div class="row clearfix main">
          <div class="col-xs-1"></div>
          <div class="padded search-div col-xs-10">
            <div class="search-input">
            <form id="searchform" name="searchform" method="GET" action="/">
              <input type="text" id="q" name="q" value="{$q}" class="form-control" placeholder="Traveling somewhere?" />
                  <img class="search" src="/images/search.png"/>
            </form>  
            </div>
            <div id="suggestion-box" class="suggestion-box">
                  <div id="suggestions"><!--suggestions here--></div>            
            </div>
          </div>
{
(:      <div class="header" arcsize="5 5 0 0">
        <label>Search</label>
        <form id="searchform" name="searchform" method="GET" action="/">
	        <input type="text" id="q" name="q" class="searchbox" value="{$q}"/>
	          <div id="suggestions"><!--suggestions here--></div>
	          <div id="searchbutton" class="searchbutton">
              <button type="submit" title="Run Search"><img src="/images/search.png"/></button>
            </div>
	      </form>
      </div>:)
}
      <div class="col-xs-1"></div>
      <div class="padded row clearfix main">
      <div class="col-xs-1"></div>

        { $sidebar }
      
      <div class="col-xs-8">
        { $view }
      </div>

      <div class="col-xs-1"></div>
      </div>
      <div class="bottom50"></div>

      <div class="row clearfix footer">
        <span class="copyright">&copy; 2017, chvrg.</span>
        <a href="/page/help">Help</a>
        <span class="pipe"> </span>
        <a href="/page/contact">Contact Us</a>
        <span class="pipe"> </span>
        <a href="/page/terms">Terms of Use</a>
      </div>
      </div>
    </div>

  </body>
</html>