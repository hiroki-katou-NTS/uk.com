package nts.uk.ctx.at.aggregation.dom.form9;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.layer.app.file.storage.StoredFileInfo;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class Form9LayoutTest {
	
	@Injectable
	private Form9Layout.Require require;
	
	@Test
	public void testGetter(
				@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable
			,	@Injectable Form9Cover cover) {
		
		val result = new Form9Layout(new Form9Code("01"), new Form9Name("name")
				,	true, false
				,	cover, nursingTable
				,	nursingAideTable, Optional.of("tempalteFileId"));
		
		NtsAssert.invokeGetters(result);
		
	}

	/**
	 * target: create
	 * pattern: 出力レイアウはシステム固定, テンプレートIDがある
	 * except: Msg_2279
	 * 
	 */
	@Test
	public void testCreate_layout_is_system_templateId_not_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		NtsAssert.businessException("Msg_2279", () ->{
			Form9Layout.create(
						new Form9Code( "01" )//DUMMY
					,	new Form9Name( "name" )//DUMMY
					,	true //システム固定
					,	false//DUMMY
					,	cover, nursingTable//DUMMY
					,	nursingAideTable//DUMMY
					,	Optional.of( "templateId" ));//テンプレートIDがある
		});
		
	}
	
	/**
	 * target: create
	 * pattern: 出力レイアウはユーザー定義, テンプレートIDはempty
	 * except: Msg_2280
	 * 
	 */
	@Test
	public void testCreate_layout_is_user_templateId_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		NtsAssert.businessException("Msg_2280", () ->{
			Form9Layout.create(
						new Form9Code( "01" )//DUMMY
					,	new Form9Name( "name" )//DUMMY
					,	false //ユーザー定義
					,	false//DUMMY
					,	cover, nursingTable//DUMMY
					,	nursingAideTable//DUMMY
					,	Optional.empty());//テンプレートIDがempty
		});
	}
		
	/**
	 * target: create
	 * pattern: 出力レイアウはユーザー定義, テンプレートID not empty
	 * except: success
	 * 
	 */
	@Test
	public void testCreate_layout_is_user_templateId_not_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		val code = new Form9Code( "01" );
		val name = new Form9Name( "name" );
		val templateId = "templateId";
		
		//Act
		val layout = Form9Layout.create( code//DUMMY
					,	name//DUMMY
					,	false //ユーザー定義
					,	true//DUMMY
					,	cover, nursingTable//DUMMY
					,	nursingAideTable//DUMMY
					,	Optional.of(templateId));//テンプレートID not empty
		
		//Assert
		assertThat( layout.getCode()).isEqualTo( code );
		assertThat( layout.getName() ).isEqualTo( name );
		assertThat( layout.isSystemFixed() ).isFalse();
		assertThat( layout.isUse() ).isTrue();
		assertThat( layout.getCover() ).isEqualTo( cover );
		assertThat( layout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( layout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( layout.getTemplateFileId().get()).isEqualTo( templateId );

	}
	
	/**
	 * target: create
	 * pattern: 出力レイアウはシステム固定, テンプレートIDはempty
	 * except: success
	 * 
	 */
	@Test
	public void testCreate_layout_is_system_templateId_empty(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {

		val code = new Form9Code( "01" );
		val name = new Form9Name( "name" );
		
		//Act
		val layout = Form9Layout.create( code//DUMMY
					,	name//DUMMY
					,	true //ユーザー定義
					,	true//DUMMY
					,	cover, nursingTable//DUMMY
					,	nursingAideTable//DUMMY
					,	Optional.empty());//テンプレートIDはempty
		
		//Assert
		assertThat( layout.getCode()).isEqualTo( code );
		assertThat( layout.getName() ).isEqualTo( name );
		assertThat( layout.isSystemFixed() ).isTrue();
		assertThat( layout.isUse() ).isTrue();
		assertThat( layout.getCover() ).isEqualTo( cover );
		assertThat( layout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( layout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( layout.getTemplateFileId()).isEmpty();
		
	}
	
	/**
	 * target: copy
	 * pattern: 出力レイアウはユーザー定義
	 */
	@Test
	public void testCopy_user(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		val destinationCode = new Form9Code("02");
		val destinationName = new Form9Name("destinationName");
		val tempalteFileId = "tempalteFileId";
		val copyResource = Form9Layout.create(
						new Form9Code("01") , new Form9Name("name"), false, true
					,	cover, nursingTable, nursingAideTable
					,	Optional.of(tempalteFileId));
		
		//Act
		val destinationLayout = copyResource.copy(require, destinationCode, destinationName);
		
		//Assert
		assertThat( destinationLayout.getCode()).isEqualTo( destinationCode );
		assertThat( destinationLayout.getName() ).isEqualTo( destinationName );
		assertThat( destinationLayout.isSystemFixed() ).isFalse();
		assertThat( destinationLayout.isUse() ).isTrue();
		assertThat( destinationLayout.getCover() ).isEqualTo( cover );
		assertThat( destinationLayout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( destinationLayout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( destinationLayout.getTemplateFileId().get()).isEqualTo( tempalteFileId );
		
	}
	
	/**
	 * target: copy
	 * pattern: 出力レイアウはシステム固定
	 */
	@Test
	public void testCopy_system(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		val destinationCode = new Form9Code("02");
		val destinationName = new Form9Name("destinationName");
		val fileId = "fileId";
		val copyResource = Form9Layout.create(
						new Form9Code("01")
					,	new Form9Name("name")
					,	true//システム固定
					,	true//DUMMY
					,	cover, nursingTable, nursingAideTable
					,	Optional.empty());
		
		val storeFileInfo = Helper.createStoredFileInfo( fileId );
		
		new Expectations() {
			{
				require.saveFile((String) any);
				result = storeFileInfo;
			}
		};
		
		//Act
		val destinationLayout = copyResource.copy(require, destinationCode, destinationName);
		
		//Assert
		assertThat( destinationLayout.getCode()).isEqualTo( destinationCode );
		assertThat( destinationLayout.getName() ).isEqualTo( destinationName );
		assertThat( destinationLayout.isSystemFixed() ).isFalse();
		assertThat( destinationLayout.isUse() ).isTrue();
		assertThat( destinationLayout.getCover() ).isEqualTo( cover );
		assertThat( destinationLayout.getNursingAideTable() ).isEqualTo( nursingAideTable );
		assertThat( destinationLayout.getNursingTable() ).isEqualTo( nursingTable );
		assertThat( destinationLayout.getTemplateFileId().get()).isEqualTo( fileId );
		
	}
	
	
	/**
	 * target: getFileName
	 * pattern: 出力レイアウはシステム固定 
	 * except: ファイル名 = コード ＋ "_" + 名称 + ".xlsx"
	 * 
	 */
	@Test
	public void testGetFileName_layout_is_systemFixed(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val code = new Form9Code("01");
		val name = new Form9Name("name");
		// 期待値
		val exceptResult = "01_name.xlsx";
		
		val layout = Helper.createForm9Layout(
					code , name
				,	true //システム固定
				,	Optional.empty());
		//Act
		val result = layout.getFileName(require);
		
		//Assert
		assertThat( result ).isEqualTo( exceptResult );
		
	}
	
	/**
	 * target: getFileName
	 * pattern: 出力レイアウはユーザー定義
	 * except: ファイルIDの名称
	 * 
	 */
	@Test
	public void testGetFileName_layout_is_user(
				@Injectable Form9Cover cover
			,	@Injectable Form9NursingTable nursingTable
			,	@Injectable Form9NursingAideTable nursingAideTable) {
		
		val code = new Form9Code( "01" );
		val name = new Form9Name( "name" );
		val templateId = "templateId";
		// 期待値
		val fileNameExcepted = "template.xlsx";
		val storeFileInfo = StoredFileInfo.createNewTemporaryWithId(
					templateId, fileNameExcepted
				,	"mimeType"//DUMMY
				,	2300);//DUMMY
				
		
		val layout = Helper.createForm9Layout(code
				,	name
				,	false //ユーザー定義
				,	Optional.of( templateId ));
		
		new Expectations() {
			{
				require.getInfo((String) any);
				result = Optional.of( storeFileInfo );
			}
		};
		
		//Act
		val result = layout.getFileName(require);
		
		//Assert
		assertThat( result ).isEqualTo( fileNameExcepted );
		
	}
	
	private static class Helper{
		@Injectable
		private static Form9Cover cover;
			
		@Injectable
		private static Form9NursingTable nursingTable;
			
		@Injectable
		private static Form9NursingAideTable nursingAideTable;
		
		/**
		 * 様式９の出力レイアウトを作る
		 * @param code コード
		 * @param name 名称
		 * @param isSystemFixed システム固定か
		 * @param tempalteFileId テンプレート ファイルID
		 * @return
		 */
		public static Form9Layout createForm9Layout(Form9Code code, Form9Name name
				, boolean isSystemFixed, Optional<String> tempalteFileId) {
			
			return Form9Layout.create(
						code, name, isSystemFixed, true
					,	cover, nursingTable, nursingAideTable
					,	tempalteFileId);
		}
		
		/**
		 * ファイル情報を作る
		 * @param fileId ファイルID
		 * @return
		 */
		public static StoredFileInfo createStoredFileInfo(String fileId) {
			return StoredFileInfo.createNewTemporaryWithId(
					fileId
				,	"fileName"//DUMMY
				,	"mimeType"//DUMMY
				,	2300);//DUMMY
		}
	}
}
