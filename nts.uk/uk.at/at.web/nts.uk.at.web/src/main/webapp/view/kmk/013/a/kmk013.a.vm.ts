module nts.uk.at.view.kmk013.a {
//   import service = nts.uk.at.view.kmk013.a.service;
    export module viewmodel {
        export class ScreenModel {

            isManageFlexTime: KnockoutObservable<boolean>;
            isModifiedLabor: KnockoutObservable<boolean>;
            
            constructor() {
                let self = this;

                self.isManageFlexTime = ko.observable(true);
                self.isModifiedLabor = ko.observable(true);
            }
            
            startPage(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();
                service.getDomainSet().done((data: ScreenSetting) => {

                    self.isManageFlexTime(data.flexWorkManagement == 1 ? true : false);
                    self.isModifiedLabor(data.useAggDeformedSetting == 1 ? true : false);
                    dfd.resolve();    
                });
                return dfd.promise();
            }
            
            openDialogB(): void  {
                nts.uk.request.jump("/view/kmk/013/b/index.xhtml");
            }
            openDialogC(): void  {
                nts.uk.request.jump("/view/kmk/013/c/index.xhtml");
            }
            openDialogD(): void  {
                nts.uk.request.jump("/view/kmk/013/d/index.xhtml");
            }
            openDialogE(): void  {
                nts.uk.request.jump("/view/kmk/013/e/index.xhtml");
            }
            openDialogG(): void  {
                nts.uk.request.jump("/view/kmk/013/g/index.xhtml");
            }
            openDialogH(): void  {
                nts.uk.request.jump("/view/kmk/013/h/index.xhtml");
            }
            openDialogI(): void  {
                nts.uk.request.jump("/view/kmk/013/i/index.xhtml");
            }
            openDialogJ(): void  {
                nts.uk.request.jump("/view/kmk/013/j/index.xhtml");
            }
            openDialogK(): void  {
                nts.uk.request.jump("/view/kmk/013/k/index.xhtml");
            }
            openDialogL(): void  {
                nts.uk.request.jump("/view/kmk/013/l/index.xhtml");
            }
            openDialogM(): void  {
                nts.uk.request.jump("/view/kmk/013/m/index.xhtml");
            }
            openDialogN(): void  {
                nts.uk.request.jump("/view/kmk/013/n/index.xhtml");
            }
            
             public exportExcel(): void {
                var self = this;
                nts.uk.ui.block.grayout();

                service.saveAsExcel().done(function() {
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError({ messageId: error.messageId });
                }).always(function() {
                    nts.uk.ui.block.clear();
                });
            }
            
           
        }
    }

    interface ScreenSetting {
        flexWorkManagement: number; /** フレックス勤務の設定 */
        useAggDeformedSetting: number; /** 変形労働を使用する */
        optionLicenseCustomize: boolean;
    }
}