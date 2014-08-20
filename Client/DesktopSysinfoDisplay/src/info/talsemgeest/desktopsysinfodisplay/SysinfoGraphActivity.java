package info.talsemgeest.desktopsysinfodisplay;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SysinfoGraphActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sysinfo_graph);

		Intent intent = getIntent();

		final boolean demo = intent.getBooleanExtra(MainActivity.DEMO, false);

		String ip = intent.getStringExtra(MainActivity.IP);
		String port = intent.getStringExtra(MainActivity.PORT);
		final String uri = "http://" + ip + ":" + port + "/";

		// Start update thread
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					while (!isInterrupted()) {
						Thread.sleep(1000);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {

								try {
									if (!demo) {
										//Get system info
										SysInfo sysinfo = new SysInfoRetriever()
												.execute(uri).get();

										if (sysinfo != null) {
											TextView errorMessage = (TextView) findViewById(R.id.connect_error);
											errorMessage
													.setVisibility(View.GONE);
											setCpuPercent(sysinfo.getCpu());
											setMemPercent(sysinfo.getMem());
										} else {
											// Cannot connect to server, so show
											// error message.
											TextView errorMessage = (TextView) findViewById(R.id.connect_error);
											errorMessage
													.setVisibility(View.VISIBLE);
										}
									}
									else {
										//Demo mode enabled, showing random values
										Random r = new Random();
										int cpuPercent = r.nextInt(100 - 1) + 1;
										int memPercent = r.nextInt(75 - 50) + 50;
										
										setCpuPercent((double) cpuPercent);
										setMemPercent((double) memPercent);
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				} catch (InterruptedException e) {
				}
			}
		};

		t.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sysinfo_graph, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Update CPU graph and text
	 * 
	 * @param percent
	 *            Percentage of CPU utilization.
	 */
	public void setCpuPercent(Double percent) {

		TextView cpuView = (TextView) findViewById(R.id.cpu_text);
		int cpuPercent = percent.intValue();
		cpuView.setText(cpuPercent + "%");
		ProgressBar cpuPb = (ProgressBar) findViewById(R.id.cpu_graph);
		cpuPb.setProgress(cpuPercent);
	}

	/**
	 * Update RAM graph and text
	 * 
	 * @param percent
	 *            Percentage of RAM in use.
	 */
	public void setMemPercent(Double percent) {
		TextView memView = (TextView) findViewById(R.id.mem_text);
		int memPercent = percent.intValue();
		memView.setText(memPercent + "%");
		ProgressBar memPb = (ProgressBar) findViewById(R.id.mem_graph);
		memPb.setProgress(memPercent);

	}
}
