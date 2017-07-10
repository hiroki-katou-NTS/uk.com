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
                      nts.uk.ui.windows.sub.modal("/view/kmk/005/b/index.xhtml", {title: "蜉�邨ｦ鬆�逶ｮ縺ｮ險ｭ螳�"});
                    } 
                    dfd.resolve();
                }).fail(function(res) {
//                    window.alert("fail");
                });
                
                return dfd.promise();
            }
            
            openTimeItem(): void  {
                nts.uk.ui.windows.sub.modal("/view/kmk/005/b/index.xhtml", {title: "蜉�邨ｦ鬆�逶ｮ縺ｮ險ｭ螳�"});
            }
            
            openUnitSetting(): void   {
                nts.uk.ui.windows.sub.modal("/view/kmk/005/d/index.xhtml", {title: "蛻ｩ逕ｨ蜊倅ｽ阪�ｮ險ｭ螳�"});
            }
            
            openTimeItemSetting(): void   {
                nts.uk.ui.windows.sub.modal("/view/kmk/005/e/index.xhtml", {title: "閾ｪ蜍戊ｨ育ｮ励�ｮ險ｭ螳�"});
            }
            
            openTimeSheetItem(): void {
                 nts.uk.ui.windows.sub.modal("/view/kmk/005/f/index.xhtml", {title: "蜉�邨ｦ譎る俣蟶ｯ縺ｮ逋ｻ骭ｲ"});
            }
            openBonusPay(): void
            {
                nts.uk.request.jump("/view/kmk/005/g/index.xhtml");
            }
        }      
    }
}