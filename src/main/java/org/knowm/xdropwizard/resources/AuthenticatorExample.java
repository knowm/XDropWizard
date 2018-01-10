/**
 * Copyright 2015-2018 Knowm Inc. (http://knowm.org) and contributors.
 * Copyright 2013-2015 Xeiam LLC (http://xeiam.com) and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.knowm.xdropwizard.resources;

import java.util.Optional;

import org.knowm.xdropwizard.business.User;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

/**
 * @author timmolter
 */
public class AuthenticatorExample implements Authenticator<BasicCredentials, User> {
  @Override public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
    if ("Kgfgusd3450m_88".equals(credentials.getPassword())) {
      return Optional.of(new User(credentials.getUsername()));
    }
    return Optional.empty();
  }
}