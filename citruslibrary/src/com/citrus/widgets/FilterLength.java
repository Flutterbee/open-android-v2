/*
   Copyright 2014 Citrus Payment Solutions Pvt. Ltd.
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
     http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package com.citrus.widgets;

public enum FilterLength {
	VISA("19"),
	MCRD("19"),
	MTRO("23"),
	DINERCLUB("19"),
	JCB("19"),
	AMEX("18"),
	DISCOVER("19");
	
	
	private String length;
	
	private FilterLength(String length) {
		this.length = length;
	}
	
	public String getLength() {
		return length;
	}
	
	public static FilterLength fromString(String text) {
		return FilterLength.valueOf(text);
	}
}
