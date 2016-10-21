package com.nymi.api.wrapper;

import java.security.SecureRandom;

import com.nymi.api.LibNapi;
import com.nymi.api.wrapper.NymiApi.HapticNotification;

public class NymiProvision {

	private SecureRandom rng = new SecureRandom();
	private String pid = "";
	NymiApi napi;
	
//	Map<String, NeaCallback> nymiProvisions = Map<String, NeaCallback>();

	public NymiProvision() {
		SeedRng();
	}
	
	public NymiProvision(NymiProvision other) {
		pid = other.getPid();
		SeedRng();
	}

	public NymiProvision(String npid) {
		pid = npid;
		SeedRng();
		}

	public String getPid() {
		return pid;
	}
	
	private void SeedRng() {
		SecureRandom seedrng = new SecureRandom();
		byte[] seed = seedrng.generateSeed(5);
		rng.setSeed(seed);
	}

	public void getRandom() {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
		exchange += "random" + getPid();
	    LibNapi.INSTANCE.jsonNapiPutD(GenJson.get_random(getPid(),exchange));
	}

	public void createSymmetricKey(Boolean guarded) {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
	    exchange += "createsymkey" + getPid();
	    String createsk = GenJson.create_symkey(getPid(),guarded,exchange);
	    System.out.println("sending msg: " + createsk);
	    LibNapi.INSTANCE.jsonNapiPutD(createsk);
	}

	public void getSymmetricKey() {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
		exchange += "getsymkey" + getPid();
		LibNapi.INSTANCE.jsonNapiPutD(GenJson.get_symkey(getPid(),exchange));
	}

	public void signMessage(String msghash) {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
		exchange += "sign" + getPid();
		LibNapi.INSTANCE.jsonNapiPutD(GenJson.sign_msg(getPid(), msghash, exchange));
	}

	public void createTotpKey(String totpKey, Boolean guarded) {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
		exchange += "createTotp" + getPid();
		LibNapi.INSTANCE.jsonNapiPutD(GenJson.set_totp(getPid(),totpKey,guarded,exchange));
	}

	public void getTotpKey() {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
		exchange += "getTotp" + getPid();
		LibNapi.INSTANCE.jsonNapiPutD(GenJson.get_totp(getPid(), exchange));
	}

	public void sendNotification(HapticNotification notifyType) {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
		exchange += "notify" + getPid();
		LibNapi.INSTANCE.jsonNapiPutD(GenJson.notify(getPid(), notifyType == NymiApi.HapticNotification.NOTIFY_POSITIVE, exchange));
	}

	public void getDeviceInfo() {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
	    exchange += "deviceinfo" + getPid();
	    LibNapi.INSTANCE.jsonNapiPutD(GenJson.get_info(exchange));
	}

	public void revokeKey(NymiApi.KeyType keyType) {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
	    exchange += "deviceinfo" + getPid();

	    String keyStr = "";
	    switch(keyType) {
	        case SYMMETRIC:
	        	keyStr = "symmetric"; break;
	        case TOTP:
	        	keyStr = "totp"; break;
	        default:
	        	break;
	    }

	    LibNapi.INSTANCE.jsonNapiPutD(GenJson.delete_key(getPid(),keyStr,exchange));
	}

	public void revokeProvision(Boolean onlyIfAuthenticated) {
		String exchange = ((Integer)rng.nextInt(9999)).toString();;
	    exchange += "revokeprovision" + getPid();
	    LibNapi.INSTANCE.jsonNapiPutD(GenJson.revoke_provision(getPid(),onlyIfAuthenticated,exchange));
	}
	
}
