package nts.uk.ctx.sys.shared.dom.web.menu;


import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
public class WebMenuService {

//	@Inject
//	private WebMenuRepository menuRepository;
	
	public WebMenuSet get() {
		// TODO: Get user info from session to authorize
//		PermissionContext ctx = new PermissionContext();
//		return menuRepository.get().filter(ctx).getCategories();
		
		// Create fake data to test
		List<WebMenuCategory> categories = new ArrayList<WebMenuCategory>() {
			private static final long serialVersionUID = 1L;

			{
				add(new WebMenuCategory() {
					{
						setName("前準備");
						setBackgroundColor("#F29B03");
						List<TitleMenu> items = new ArrayList<>();
						items.add(new TitleMenu() {
							{
								setName("システム");
								setTitleColor("#CAFC86");
								setImagePath("../../catalog/images/hawaii-bg.jpg");
								List<MenuItem> mItems = new ArrayList<>();
								mItems.add(new MenuItem() {
									{
										setName("各種機能の設定");
										setPath("");
									}
								});
								mItems.add(new MenuItem() {
									{
										setName("ユーザーの登録");
										setPath("");
									}
								});
								mItems.add(new MenuItem() {
									{
										setName("ロールの登録");
										setPath("");
									}
								});
								mItems.add(MenuItem.separator());
								mItems.add(new MenuItem("トップページの設定", null));
								mItems.add(new MenuItem("タイトルメニューの設定", null));
								setItems(mItems);
							}
						});
						items.add(new TitleMenu() {
							{
								setName("マスタ");
								setTitleColor("#D1FBF9");
								setImagePath("../../catalog/images/valentine-bg.jpg");
								List<MenuItem> items = new ArrayList<>();
								items.add(new MenuItem("部門職場登録", null));
								items.add(new MenuItem("名称の登録", null));
								items.add(MenuItem.separator());
								items.add(new MenuItem("職位登録", null));
								items.add(new MenuItem("分類登録", null));
								setItems(items);
							}
						});
						items.add(new TitleMenu() {
							{
								setName("申請承認設定");
								setTitleColor("#E0D1FB");
								setImagePath("");
								List<MenuItem> items = new ArrayList<>();
								items.add(new MenuItem("申請の前準備", null));
								items.add(new MenuItem("承認者の登録", null));
								items.add(new MenuItem("代行者の登録", null));
								setItems(items);
							}
						});
						setTitles(items);
					}
				});
				
				add(new WebMenuCategory() {
					{
						setName("個人情報");
						setBackgroundColor("#812EF8");
						List<MenuItem> items = new ArrayList<>();
						items.add(new MenuItem() {
							{
								setName("Item1_1");
								setPath("item1_1");
							}
						});
						
						items.add(new MenuItem() {
							{
								setName("Item1_2");
								setPath("item1_2");
							}
						});
						setItems(items);
					}
				});
				
				add(new WebMenuCategory() {
					{
						setName("勤務予定");
						setBackgroundColor("#0598CB");
						List<MenuItem> items = new ArrayList<>();
						items.add(new MenuItem() {
							{
								setName("Item2_1");
								setPath("item2_1");
							}
						});
						
						items.add(new MenuItem() {
							{
								setName("Item2_2");
								setPath("item2_2");
							}
						});
						setItems(items);
					}
				});
				
				add(new WebMenuCategory() {
					{
						setName("申請");
						setBackgroundColor("#B72104");
						List<MenuItem> items = new ArrayList<>();
						items.add(new MenuItem() {
							{
								setName("Item3_1");
								setPath("item3_1");
							}
						});
						
						items.add(new MenuItem() {
							{
								setName("Item3_2");
								setPath("item3_2");
							}
						});
						setItems(items);
					}
				});
			}
		};
		
		return new WebMenuSet(categories);
	}
}
