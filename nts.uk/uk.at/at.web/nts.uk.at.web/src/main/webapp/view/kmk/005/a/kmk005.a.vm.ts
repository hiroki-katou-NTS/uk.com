module nts.uk.at.view.kmk005.a {
    export module viewmodel {
        export class ScreenModel {
            constructor() {
               
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                
               service.checkInit().done(function(item: number) {
                    if (item == 0) {
                      nts.uk.ui.windows.sub.modal("/view/kmk/005/b/index.xhtml", {title: "加給項目の設定"});
                    } 
                    dfd.resolve();
                }).fail(function(res) {
//                    window.alert("fail");
                });
                
                return dfd.promise();
            }
            
            openTimeItem(): void  {
                nts.uk.ui.windows.sub.modal("/view/kmk/005/b/index.xhtml", {title: "加給項目の設定"});
            }
            
            openUnitSetting(): void   {
                nts.uk.ui.windows.sub.modal("/view/kmk/005/d/index.xhtml", {title: "利用単位の設定"});
            }
            
            openTimeItemSetting(): void   {
                nts.uk.ui.windows.sub.modal("/view/kmk/005/e/index.xhtml", {title: "自動計算の設定"});
            }
            
            openTimeSheetItem(): void {
                 nts.uk.request.jump("/view/kmk/005/f/index.xhtml");
            }
            openBonusPay(): void
            {
                nts.uk.request.jump("/view/kmk/005/g/index.xhtml");
            }
        }      
    }
}