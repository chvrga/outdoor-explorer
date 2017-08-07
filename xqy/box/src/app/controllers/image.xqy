xquery version "1.0-ml";

module namespace i = "http://marklogic.com/roxy/controller/image";

(: The request library provides awesome helper methods to abstract get-request-field :)
import module namespace req = "http://marklogic.com/roxy/request" at "/roxy/lib/request.xqy";

(: the controller helper library provides methods to control which view and template get rendered :)
import module namespace ch = "http://marklogic.com/roxy/controller-helper" at "/roxy/lib/controller-helper.xqy";

import module namespace s = "http://marklogic.com/roxy/models/search" at "/app/models/search-lib.xqy";


declare function i:main()
{
	(: Tab here to start coding :)

	let $uri := xdmp:get-request-field("uri")
	let $mimetype := xdmp:uri-content-type($uri) 
	return
	 if(fn:doc($uri))
	 then
		(
			xdmp:set-response-content-type($mimetype),
			fn:doc($uri)
		)
	 else ()	
};


declare function i:document-details()
{
	(: Tab here to start coding :)
	let $uri as xs:string := req:get("uri", "", "type=xs:string")
	let $page := req:get("page", 1, "type=xs:int")
	return
	(
		ch:add-value("response", s:document($uri))
	),
	ch:use-view((), "xml"),
	ch:use-layout((), "xml")

};
