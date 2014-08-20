package info.talsemgeest.desktopsysinfodisplay;

public class SysInfo {
	private Double cpu;
	private Double mem;
	
	/**
	 * Stores system info.
	 * @param cpu Percentage of cpu utilization.
	 * @param mem Percentage of RAM in use.
	 */
	public SysInfo(Double cpu, Double mem) {
		this.cpu = cpu;
		this.mem = mem;
	}
	
	public Double getCpu() {
		return cpu;
	}
	public void setCpu(Double cpu) {
		this.cpu = cpu;
	}
	public Double getMem() {
		return mem;
	}
	public void setMem(Double mem) {
		this.mem = mem;
	}

}
