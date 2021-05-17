package nts.uk.screen.at.app.ksu001.summarycategory;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SummaryCategoryDto {
	// 職場計.利用カテゴリ一覧
	public List<Integer> useCategoriesWorkplace = Collections.emptyList();
	// 個人計.利用カテゴリ一覧
	public List<Integer> useCategoriesPersonal = Collections.emptyList();
	
	
}
