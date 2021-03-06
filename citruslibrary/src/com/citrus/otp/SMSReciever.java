package com.citrus.otp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;

public class SMSReciever extends BroadcastReceiver {
	String otp="", js="";
	
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			SmsMessage[] msgs = null;
			
		if (bundle != null) {
			
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			
			for (int i=0; i<msgs.length; i++) {
				
			msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			
			try {
				String senderBank = msgs[i].getOriginatingAddress();
				
				String message = msgs[i].getMessageBody();
				
				String senderbank = BankOTP.bankName(senderBank).toString();
				
				String otplength = OtpLength.valueOf(senderbank).getLength();
				
				otp = GetOtp.extractOTP(message, otplength);
				
				js = GetOtp.getJS(otp, senderbank);
				
			} catch (IllegalArgumentException e) {
				
			}
			
			
		}
			
			if (!TextUtils.isEmpty(otp.toString())) {
				Intent messageIntent = new Intent("single_tap_otp");
				messageIntent.putExtra("otp", otp);
				messageIntent.putExtra("js", js);
				context.sendBroadcast(messageIntent);
			}
			
		}
	
	}
}