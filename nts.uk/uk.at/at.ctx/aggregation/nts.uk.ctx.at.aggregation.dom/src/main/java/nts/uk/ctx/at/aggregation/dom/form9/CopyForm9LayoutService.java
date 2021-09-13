package nts.uk.ctx.at.aggregation.dom.form9;
/**
 * 様式９の出力レイアウトを複製する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.様式９の出力レイアウトを複製する
 * @author lan_lt
 *
 */

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;

public class CopyForm9LayoutService {
	
	/**
	 * 複製する
	 * @param require
	 * @param copyResource 複製元
	 * @param destinationCode 複製先コード
	 * @param destinationName 複製先名称
	 * @param isOverwrite 上書きするか
	 * @return
	 */
	public static AtomTask copy(Require require, Form9Layout copyResource
			,	Form9Code destinationCode
			,	Form9Name destinationName
			,	boolean isOverwrite) {
		
		Optional<Form9Layout> oldCopyDestination = require.getForm9Layout(destinationCode);
		
		if(oldCopyDestination.isPresent()) {
			
			if(oldCopyDestination.get().isSystemFixed()) {
				throw new BusinessException("Msg_2252", destinationCode.v());
			}
			
			if(!isOverwrite) {
				throw new BusinessException("Msg_2239");
			}
			
		}
		
		return AtomTask.of(() ->{
			
			if(oldCopyDestination.isPresent()) {
				require.deleteForm9Layout(destinationCode);
			}
			
			Form9Layout newLayout = copyResource.copy(require, destinationCode, destinationName);
			require.insertForm9Layout(newLayout);
			
		});
	}
	
	public static interface Require extends Form9Layout.Require{
		
		/**
		 * 様式９の出力レイアウトを取得する
		 * @param form9Code 様式９のコード
		 * @return
		 */
		Optional<Form9Layout> getForm9Layout(Form9Code form9Code);
		
		/**
		 * 様式９の出力レイアウトを追加する
		 * @param layout 様式９の出力レイアウト
		 */
		void insertForm9Layout(Form9Layout layout);
		
		/**
		 * 様式９の出力レイアウトを削除する
		 * @param form9Code 様式９のコード
		 */
		void deleteForm9Layout(Form9Code form9Code);
		
	}

}
