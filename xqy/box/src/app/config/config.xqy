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

module namespace c = "http://marklogic.com/roxy/config";

import module namespace def = "http://marklogic.com/roxy/defaults" at "/roxy/config/defaults.xqy";

declare namespace rest = "http://marklogic.com/appservices/rest";

(:
 : ***********************************************
 : Overrides for the Default Roxy control options
 :
 : See /roxy/config/defaults.xqy for the complete list of stuff that you can override.
 : Roxy will check this file (config.xqy) first. If no overrides are provided then it will use the defaults.
 :
 : Go to https://github.com/marklogic/roxy/wiki/Overriding-Roxy-Options for more details
 :
 : ***********************************************
 :)
declare variable $c:ROXY-OPTIONS :=
  <options>
    <layouts>
      <layout format="html">two-column</layout>
    </layouts>
  </options>;

(:
 : ***********************************************
 : Overrides for the Default Roxy scheme
 :
 : See /roxy/config/defaults.xqy for the default routes
 : Roxy will check this file (config.xqy) first. If no overrides are provided then it will use the defaults.
 :
 : Go to https://github.com/marklogic/roxy/wiki/Roxy-URL-Rewriting for more details
 :
 : ***********************************************
 :)
    (: get-file.xqy?uri={xdmp:url-encode(doc/*:logo/string())} :)

declare variable $c:ROXY-ROUTES :=
  <routes xmlns="http://marklogic.com/appservices/rest">
    <request uri="^/my/awesome/route" />

    <request uri="^/[S|s]earch\.?(\w*)/?$" endpoint="/roxy/query-router.xqy">
      <uri-param name="controller" default="search"></uri-param>
      <uri-param name="func" default="main"></uri-param>
      <uri-param name="format" default="json"></uri-param>
      <uri-param name="docid">$1</uri-param>
      <http method="GET"/>
      <http method="HEAD"/>
    </request>

    <request uri="^/image?uri=(.+)$" endpoint="/roxy/query-router.xqy">
      <uri-param name="controller">image</uri-param>
      <uri-param name="func">main</uri-param>
      <uri-param name="uri">$1</uri-param>
      <http-method>GET</http-method>
    </request>
    
    <request uri="^/general?uri=(.+)$" endpoint="/roxy/query-router.xqy">
      <uri-param name="controller">general</uri-param>
      <uri-param name="func">main</uri-param>
      <uri-param name="uri">$1</uri-param>
      <http-method>GET</http-method>
    </request>

    {
      $def:ROXY-ROUTES/rest:request
    }
  </routes>;

(:
 : ***********************************************
 : What is the default language of the controllers defined in the <request>
 : sjs - Serverside JavaScript
 : xqy - XQuery (default)
 :
 : Override this setting in the build.properties with the "controller-ext" key value pair.
 :
 : ***********************************************
 :)
declare variable $c:CTRL-EXT := ("@ml.controller-ext", $def:CTRL-EXT)[fn:not(. eq "@" || "ml.controller-ext")][1];

(:
 : ***********************************************
 : A decent place to put your appservices search config
 : and various other search options.
 : The examples below are used by the appbuilder style
 : default application.
 : ***********************************************
 :)
declare variable $c:DEFAULT-PAGE-LENGTH as xs:int := 9;

declare variable $c:SEARCH-OPTIONS :=
  <options xmlns="http://marklogic.com/appservices/search">
    <search-option>unfiltered</search-option>
    <term>
      <term-option>case-insensitive</term-option>
    </term>

    <constraint name="collection">
      <collection prefix=""/>
    </constraint>

    <constraint name="user">
      <range type="xs:string" collation="http://marklogic.com/collation/en/S1/AS/T00BB" facet="true">
          <element ns="http://chvrga.org/user" name="username"/>
          <facet-option>ascending</facet-option>
      </range>
    </constraint>

    <constraint name="country">
      <range type="xs:string" collation="http://marklogic.com/collation/en/S1/AS/T00BB" facet="true">
          <element ns="http://chvrga.org/explore" name="country"/>
          <facet-option>ascending</facet-option>
      </range>
    </constraint>

    <constraint name="destination">
      <range type="xs:int" facet="true">
        <element ns="http://chvrga.org/explore" name="explore"/>
         <attribute ns="" name="id"/>
         <facet-option>limit=10</facet-option>
      </range>
    </constraint>

    <return-facets>true</return-facets>  
    <return-results>true</return-results>
    <return-query>true</return-query>
  </options>;

declare variable $c:SIDEBAR-OPTIONS := <options xmlns="http://marklogic.com/appservices/search">
    <search-option>unfiltered</search-option>
    <term>
      <term-option>case-insensitive</term-option>
    </term>

    <constraint name="user">
      <range type="xs:string" collation="http://marklogic.com/collation/en/S1/AS/T00BB" facet="true">
          <element ns="http://chvrga.org/user" name="username"/>
          <facet-option>ascending</facet-option>
      </range>
    </constraint>

    <constraint name="country">
      <range type="xs:string" collation="http://marklogic.com/collation/en/S1/AS/T00BB" facet="true">
          <element ns="http://chvrga.org/explore" name="country"/>
          <facet-option>ascending</facet-option>
      </range>
    </constraint>

    <return-facets>true</return-facets>  
    <return-results>true</return-results>
    <return-query>true</return-query>
  </options>;


(:

    <constraint name="username">
            <range type="xs:string" collation="http://marklogic.com/collation/en/S1/AS/T00BB" facet="true">
                <element ns="http://chvrga.org/list" name="username"/>
                <facet-option>ascending</facet-option>
            </range>
    </constraint>
    <constraint name="destinationId">
            <range type="xs:string" collation="http://marklogic.com/collation/" facet="true">
                <element ns="http://chvrga.org/list" name="destinationId"/>
                <facet-option>ascending</facet-option>
            </range>
    </constraint>
    <constraint name="list_type">
      <range type="xs:string" collation="http://marklogic.com/collation/" facet="true">
        <element ns="http://chvrga.org/list" name="list"/>
         <attribute ns="" name="type"/>
         <facet-option>limit=10</facet-option>
      </range>
    </constraint>

:)

(:
 : Labels are used by appbuilder faceting code to provide internationalization
 :)
declare variable $c:LABELS :=
  <labels xmlns="http://marklogic.com/xqutils/labels">
    <label key="user">
      <value xml:lang="en">Users</value>
    </label>
    <label key="country">
      <value xml:lang="en">Countries</value>
    </label>
    <label key="destination">
      <value xml:lang="en">Destinations</value>
    </label>
  </labels>;