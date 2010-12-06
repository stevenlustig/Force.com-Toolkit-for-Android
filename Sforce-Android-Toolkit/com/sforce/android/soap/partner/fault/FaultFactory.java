package com.sforce.android.soap.partner.fault;

import android.os.Bundle;

public abstract class FaultFactory {
	public static ApiFault getSoapFault(Bundle bundle){
		return new ApiFault();
	}
}
