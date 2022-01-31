package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import java.util.List;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

public class TaskChildCanonicalization implements DomainCanonicalization{

	@Override
	public ItemNoMap getItemNoMap() {
		return ItemNoMap.reflection(Items.class);
	}

	public static class Items {
		public static final int 作業枠No = 1;
		public static final int 作業コード = 2;
		public static final int 子作業コード = 3;
	}

	@Override
	public void canonicalize(RequireCanonicalize require, ExecutionContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ImportingDataMeta appendMeta(ImportingDataMeta source) {
		return source;
	}

	@Override
	public AtomTask adjust(RequireAdjsut require, ExecutionContext context, List<AnyRecordToChange> recordsToChange,
			List<AnyRecordToDelete> recordsToDelete) {
		// TODO Auto-generated method stub
		return null;
	}



//	
//	@Override
//	protected List<DomainDataColumn> getDomainDataKeys() {
//		return Arrays.asList(DomainDataColumn.CID, 
//				new DomainDataColumn("作業枠No", DataType.INT),
//				new DomainDataColumn("作業コード", DataType.STRING),
//				new DomainDataColumn("子作業コード", DataType.STRING));
//	}
//	
//	@Override
//	protected String getParentTableName() {
//		return "KSRMT_TASK_CHILD";
//	}
//
//	@Override
//	protected List<String> getChildTableNames() {
//		return Collections.emptyList();
//	}
//	
//    @Override
//    protected IntermediateResult canonicalizeExtends(IntermediateResult targetResult) {
//
//    	if(targetResult.getItemByNo(Items.作業枠NO).get().getInt().intValue())
//        return replaceColorCode(targetResult, frameNo);
//    }
//  private IntermediateResult replaceChildTask(IntermediateResult targetResult, int frameNo) {
//  if(frameNo == 5) {
//      return targetResult.addCanonicalized(CanonicalItemList.of(new DataItemList(Arrays.asList(
//              new DataItem(Items.子作業1作業コード, null),
//              new DataItem(Items.子作業2作業コード, null),
//              new DataItem(Items.子作業3作業コード, null),
//              new DataItem(Items.子作業4作業コード, null),
//              new DataItem(Items.子作業5作業コード, null)))));
//  }
//  return targetResult;
//}


}
