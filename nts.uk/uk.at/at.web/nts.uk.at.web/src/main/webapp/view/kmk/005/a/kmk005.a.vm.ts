module nts.uk.at.view.kmk005.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
               
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
               service.getListBonusPTimeItem().done(function(item: Array<any>) {
                    if (item === undefined || item.length == 0) {
                      nts.uk.ui.windows.sub.modeless("/view/kmk/005/b/index.xhtml", {title: "加給項目の設定"});
                    } 
                }).fail(function(res) {
                    window.alert("fail");
                });
                
                return dfd.promise();
            }
            
            openTimeItem(): void  {
                nts.uk.ui.windows.sub.modeless("/view/kmk/005/b/index.xhtml", {title: "加給項目の設定"});
            }
            
            openUnitSetting(): void   {
                nts.uk.ui.windows.sub.modeless("/view/kmk/005/d/index.xhtml", {title: "利用単位の設定"});
            }
            
            openTimeItemSetting(): void   {
                nts.uk.ui.windows.sub.modeless("/view/kmk/005/e/index.xhtml", {title: "自動計算の設定"});
            }
            
            openTimeSheetItem(): void {
                 nts.uk.ui.windows.sub.modeless("/view/kmk/005/f/index.xhtml", {title: "加給時間帯の登録"});
            }
            openBonusPay(): void
            {
                nts.uk.ui.windows.sub.modeless("/view/kmk/005/g/index.xhtml", {title: "加給時間帯の設定（会社）"});
            }
        }      
    }
}