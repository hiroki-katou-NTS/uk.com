package nts.uk.shr.com.net;

import org.apache.commons.lang3.Range;

import lombok.Getter;
import nts.arc.error.BusinessException;

@Getter
public class Ipv4Address {
	
	/** ネットワーク部1 */
	private Ipv4Part net1;

	/** ネットワーク部2 */
	private Ipv4Part net2;

	/** ホスト部1 */
	private Ipv4Part host1;

	/** ホスト部2 */
	private Ipv4Part host2;
	
	public Ipv4Address(Ipv4Part net1, Ipv4Part net2, Ipv4Part host1, Ipv4Part host2) {
		this.net1 	= net1;
		this.net2 	= net2;
		this.host1 	= host1;
		this.host2 	= host2;
	}
	
	public static Ipv4Address toAddress(String ipv4Address) {
		
		// 分解
		String[] disassembledIpAddress = ipv4Address.split("\\.", 0);

		if(!(disassembledIpAddress.length == 4)) {
			throw new BusinessException("IPアドレスの形式が正しくありません。");
		}
		
		return new Ipv4Address(
				new Ipv4Part(Integer.parseInt(disassembledIpAddress[0])), 
				new Ipv4Part(Integer.parseInt(disassembledIpAddress[1])), 
				new Ipv4Part(Integer.parseInt(disassembledIpAddress[2])), 
				new Ipv4Part(Integer.parseInt(disassembledIpAddress[3])));
	}
	
	public String toString() {
		return  this.net1.toString() + "." + 
				this.net2.toString() + "." + 
				this.host1.toString() + "." + 
				this.host2.toString();
	}
	
	private long toDecimal() {
		return 	this.net1.v() 	* (long) Math.pow(256, 3) + 
				this.net2.v() 	* (long) Math.pow(256, 2) + 
				this.host1.v() 	* (long) Math.pow(256, 1) + 
				this.host2.v();
	}
	
	public boolean compareTo(Ipv4Address target) {
		if (this.toDecimal() == target.toDecimal()) {
			return true;
		}
		return false;
	}
	
	public boolean compareRangeTo(Ipv4Address start, Ipv4Address end) {
		Range<Long> longRange = Range.between(start.toDecimal(), end.toDecimal());
		if(longRange.contains(this.toDecimal())) {
			return true;
		}
		return false;
	}
}
