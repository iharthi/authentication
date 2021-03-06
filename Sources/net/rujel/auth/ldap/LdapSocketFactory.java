// LdapSocketFactory.java

/*
 * Copyright (c) 2008, Gennady & Michael Kushnir
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 * 	•	Redistributions of source code must retain the above copyright notice, this
 * 		list of conditions and the following disclaimer.
 * 	•	Redistributions in binary form must reproduce the above copyright notice,
 * 		this list of conditions and the following disclaimer in the documentation
 * 		and/or other materials provided with the distribution.
 * 	•	Neither the name of the RUJEL nor the names of its contributors may be used
 * 		to endorse or promote products derived from this software without specific 
 * 		prior written permission.
 * 		
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.rujel.auth.ldap;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import net.rujel.reusables.SettingsReader;
import net.rujel.reusables.ssl.CustomSSLSocketFactory;

public class LdapSocketFactory extends CustomSSLSocketFactory {
	//protected static SSLContext ctx;
	
	protected LdapSocketFactory (SSLSocketFactory toUse) {
		super(toUse);
	}
	
	public static SocketFactory getDefault() {
		if(ctx == null) {
			SettingsReader prefs = SettingsReader.settingsForPath("auth.ldap.ssl", false);
			if(prefs == null)
				prefs = SettingsReader.DUMMY;
			try {
				String protocol = prefs.get("protocol", "SSL");
				ctx = SSLContext.getInstance(protocol);
				initSSLContext(ctx, prefs);
			} catch (Exception e) {
				throw new RuntimeException("Error generating ldap socket factory",e);
			}
		}
		SSLSocketFactory gen = ctx.getSocketFactory();
		return new LdapSocketFactory(gen);
	}

}
