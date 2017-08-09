xquery version "1.0-ml";

module namespace gen = "http://marklogic.com/roxy/controller/general";

(: The request library provides awesome helper methods to abstract get-request-field :)
import module namespace req = "http://marklogic.com/roxy/request" at "/roxy/lib/request.xqy";

(: the controller helper library provides methods to control which view and template get rendered :)
import module namespace ch = "http://marklogic.com/roxy/controller-helper" at "/roxy/lib/controller-helper.xqy";

import module namespace s = "http://marklogic.com/roxy/models/search" at "/app/models/search-lib.xqy";

declare function gen:main()
{
	(: Tab here to start coding :)
	  let $uri as xs:string := req:get("uri", "", "type=xs:string")
	  return
	  (
	    ch:add-value("response", s:document($uri))
	  ),
	  ch:use-view((), "xml"),
	  ch:use-layout((), "xml")
};
