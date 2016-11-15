package nts.uk.ctx.pr.proto.dom.paymentdata;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class PaymentCreateDataServiceImpl implements PaymentCreateDataService {

	@Override
	public void add(Payment payment) {
		// 1. check exists in payment_header
		//    1.1 if not exists -> continue
		//    1.2 else -> error ERO28
		
		// 2. get QSTMT_STMT_ALLOT_PS
		//    2.1 if not exists -> get QSTMT_STMT_ALLOT_CP
		
		// 3. get QSTMT_STMT_LAYOUT_HEAD
		
		// 4. get QSTMT_STMT_LAYOUT_CTG
		
		// 5. get QSTMT_STMT_LAYOUT_LINES
		
		// 6. get QSTMT_STMT_LAYOUT_DETAIL
		
		// 7. get QCAMT_ITEM
		
		// 8. get PayrollSystem from PCLMT_PERSON_EMP_CONTRACT
		
		// 9. get remain day and remain time from PHLDT_HOLIDAY_PAID
		
		// 10. check PayrollSystem
		//	  10.1 if PayrollSystem == 2 || 3 
		//		      10.1.1 get ItemCode in QSTMT_STMT_LAYOUT_DETAIL with condition CTG_ATR = 2
		//            10.1.2 if ItemCode == F206
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = REMAIN_DAYS)
		//            10.1.3 if ItemCode == F212
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = REMAIN_TIME)
		//            10.1.4 else 
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = 0)
		
		//    10.2 if PayrollSystem == 0 || 1
		//			  10.2.1 get CCAST_BASIC_CALC
		//			  10.2.2 get item in QSTMT_STMT_LAYOUT_DETAIL with condition CTG_ATR = 2
		//            10.2.3 if ItemCode == F206
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = REMAIN_DAYS)
		//            10.2.4 if ItemCode == F212
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = REMAIN_TIME)
		//            10.2.5 if ItemCode == F201 || F202
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = ???)	
		//            10.2.6 if ItemCode == F203
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = ???)
		//            10.2.7 else 
		//                      insert QSTDT_PAYMENT_DETAIL(VAL = 0)		
		
		//    10.3 else error system
		
		// 11. get PPRMT_PERSON_WAGE 
		
		// 12. get PPRMT_PERSON_COMMUTE
		
		// 13. get QSTMT_STMT_LAYOUT_DETAIL.CALC_METHOD
		
		// 14. if CALC_METHOD == 0 || 2 || 3 || 4
		//        insert QSTDT_PAYMENT_DETAIL(VAL = 0)
		
		// 15. get QCAMT_ITEM.TAX_ATR
		// 16. if TAX_ATR == 0 || 1 || 2
		//        insert QSTDT_PAYMENT_DETAIL(VAL = ???)
		// 17. if TAX_ATR == 3 || 4
		//        17.1 get QSTMT_STMT_LAYOUT_DETAIL.COMMUTE_ATR
		//        17.2 if COMMUTE_ATR == 0
		//             insert QSTDT_PAYMENT_DETAIL(VAL = ???) -> USE_OR_NOT = 1 && COMMU_MEANS_ATTR = 0
	}

}
