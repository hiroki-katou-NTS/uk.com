module nts.uk.at.view.cmm018.a {
    import flat = nts.uk.util.flatArray;
    import getText = nts.uk.resource.getText;
    import aService = nts.uk.at.view.cmm018.a.service;
    export module viewmodel {
        export class ScreenModel {
            nameCompany: KnockoutObservable<string>;
            modeCommon:KnockoutObservable<boolean> = ko.observable(true);
            isUpdate: KnockoutObservable<boolean> = ko.observable(true);
            listMode: KnockoutObservableArray<any>;
            selectedModeCode: KnockoutObservable<number> = ko.observable(0);
            constructor() {
                var self = this;
                self.nameCompany  = ko.observable('Kakashi');
                self.listMode = ko.observableArray([
                                    { code: 0, name: nts.uk.resource.getText("CMM018_15") },
                                    { code: 1, name: nts.uk.resource.getText("CMM018_16") }
                                ]);
            }
            /*
                open Dialog D, set param = {yearMonth} 
            */
            openDialogC() {
                var self = this;
            }
            
            /*
                open Dialog D, set param = {classification,yearMonth,workPlaceId,classCD}
            */
            openDialogD(value) {
                nts.uk.ui.block.invisible()
                var self = this;
            }
            
            openI(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/i/index.xhtml");
            }
            openJ(){
                 nts.uk.ui.windows.sub.modal("/view/cmm/018/j/index.xhtml");
                
            }
        }
        interface IDataJ{
            /**開始日*/
            startDate: string;
            /**終了日*/
            endDate: string;
            /**履歴ID*/
            workplaceId: string;
            /**社員ID*/
            employeeId: string;
            /**check 申請承認の種類区分*/
            check: number;
            /**「履歴を削除する」を選択する か、「履歴を修正する」を選択する か。*/
            editOrDelete: number;
            /**開始日 previous*/
            sDatePrevious: string;
            /** list history and approvalId */
            lstUpdate: Array<UpdateHistoryDto>;
        }
        class UpdateHistoryDto{
            /**承認ID*/
            approvalId: string;
            /**履歴ID*/
            historyId: string;
        }
    }
}