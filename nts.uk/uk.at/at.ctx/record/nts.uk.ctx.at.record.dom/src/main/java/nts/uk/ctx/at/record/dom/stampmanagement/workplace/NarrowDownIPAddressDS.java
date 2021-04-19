package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.shr.com.net.Ipv4Address;

/**
 * DS : 登録する前IPアドレスを絞り込む。
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.勤務場所.登録する前IPアドレスを絞り込む。
 * @author tutk
 *
 */
public class NarrowDownIPAddressDS {
	/**
	 * [1]登録する前IPアドレスを絞り込む（IP1、IP2、IP3、IP4start、IP5end）																							
	 * @param net1
	 * @param net2
	 * @param host1
	 * @param host2
	 * @param iPEnd
	 * @param require
	 * @return
	 */
	public static Map<Ipv4Address , Boolean> narrowDownIPAddressDS(int net1,int net2,int host1,int host2, int iPEnd,Require require){
		Map<Ipv4Address , Boolean> result = new HashMap<>();
		List<Ipv4Address> listIpv4Address = require.getIPAddressByStartEndIP(net1, net2, host1, host2, iPEnd);
		
		for(int h2 = host2;h2<= iPEnd;h2++) {
			Ipv4Address data =  Ipv4Address.parse(net1+"."+net2+"."+host1+"."+h2);
			Optional<Ipv4Address> opt = listIpv4Address.stream().filter(c->c.compareTo(data) == 0).findFirst();
			if (opt.isPresent()) {
				result.put(opt.get(), false);
			}else {
				result.put(data,true);
			}
		}
		return result;
	}
	
	public static interface Require {
		/**
		 * [R-1] 登録したIPアドレスを取得する。   WorkLocationRepository :[7] 契約コード、startIPとendIPでIPアドレスを取得する。
		 * @param contractCode
		 * @param net1
		 * @param net2
		 * @param host1
		 * @param host2
		 * @param endIP
		 * @return
		 */
		List<Ipv4Address> getIPAddressByStartEndIP(int net1, int net2, int host1, int host2, int endIP);
	}
}
