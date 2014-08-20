package info.talsemgeest.desktopsysinfodisplay;

import org.alexd.jsonrpc.*;

import android.os.AsyncTask;

public class SysInfoRetriever extends AsyncTask<String, Integer, SysInfo> {

	/**
	 * 
	 * Retrieve system information over JSON-RPC and create a SysInfo object
	 * with the results.
	 */
	@Override
	protected SysInfo doInBackground(String... uris) {
		String uri = uris[0];

		//Connect to server
		JSONRPCClient client = JSONRPCClient.create(uri,
				JSONRPCParams.Versions.VERSION_2);
		client.setConnectionTimeout(2000);
		client.setSoTimeout(2000);

		try {
			Double cpu = client.callDouble("get_cpu_percent");
			Double mem = client.callDouble("get_mem_percent");

			SysInfo sysinfo = new SysInfo(cpu, mem);
			return sysinfo;

		} catch (JSONRPCException e) {
			e.printStackTrace();
			return null;
		}
	}
}
