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

module namespace uv = "http://www.marklogic.com/roxy/user-view";

import module namespace form = "http://marklogic.com/roxy/form-lib" at "/app/views/helpers/form-lib.xqy";

declare default element namespace "http://www.w3.org/1999/xhtml";

declare option xdmp:mapping "false";

(:
        #{if connected}
        <li><a href="#newdestination" data-bind="click: addNewDestination"><i class="fa fa-shield"></i>&{'add'}</a></li>
        <li><a href="logout"><i class="fa fa-shield"></i>&{'signout'}</a></li> 
        #{/if} 
        #{else}
        <li><a href="login"><i class="fa fa-shield"></i>&{'signin'}</a></li>
        <li><a href="#register" data-bind="click: register"><i class="fa fa-comment"></i>&{'register'}</a></li> 
        #{/else}
:)

declare function uv:build-user($username, $profile-link, $login-link, $register-link, $logout-link)
{
  if ($username) then
    uv:welcome($username, $profile-link, $logout-link)
  else
    uv:build-login($login-link, $register-link)
};

declare function uv:welcome($username, $profile-link, $logout-link)
{
(:  
  <div class="user">
    <div class="welcome">Welcome,<a href="{$profile-link}">{$username}</a>&nbsp;</div>
    <a href="{$logout-link}" class="logout">logout</a>
  </div>
:)
        <li><a href="/newdestination" data-bind="click: addNewDestination"><i class="fa fa-shield"></i>New Destination</a></li>,
        <li><a href="{$logout-link}"><i class="fa fa-shield"></i>Sign Out</a></li> 
};

declare function uv:build-login($login-link, $register-link)
{
(:
  <div class="user">
    <form action="{$login-link}" method="POST">
      {
        form:text-input("Username:", "username", "username"),
        form:password-input("Password:", "password", "password")
      }
      <input type="submit" value="Login"/>
    </form>
    <a href="{$register-link}">register</a>
  </div>
:)

        <li><a href="{$login-link}"><i class="fa fa-shield"></i>Sign In</a></li>,
        <li><a href="{$register-link}" ><i class="fa fa-comment"></i>Register</a></li> 
};
