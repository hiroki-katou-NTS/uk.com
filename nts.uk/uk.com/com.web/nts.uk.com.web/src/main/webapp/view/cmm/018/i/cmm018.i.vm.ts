module nts.uk.com.view.cmm018.i {
    export module viewmodel {
        import vmbase = cmm018.shr.vmbase;
        import getShared = nts.uk.ui.windows.getShared;
        import setShared = nts.uk.ui.windows.setShared;
        export class ScreenModel {
            copyDataFlag: KnockoutObservable<boolean>;
            beginStartDate: KnockoutObservable<string>;
            newStartDate: KnockoutObservable<string>;
            item: KnockoutObservable<string>;
            dataSource: IData_Param;
            constructor() {
                var self = this;
                self.copyDataFlag = ko.observable(true);
                self.dataSource = nts.uk.ui.windows.getShared('CMM018I_PARAM')||
                        {name: 'Hatake Kakashi',startDate: '2021-11-02', startDateOld: '9999-12-31', check: 1, mode: 0};
                self.item = ko.observable(self.dataSource.name);
                self.beginStartDate = ko.observable(self.dataSource.startDate);
                self.newStartDate = ko.observable(null);
            }
            
            /**
             * process parameter and close dialog 
             */
            submitAndCloseDialog(): void {
                var self = this;
                if(!vmbase.ProcessHandler.validateDateInput(self.newStartDate(),self.beginStartDate())){
                    $("#startDateInput").ntsError('set', {messageId:"Msg_153"});
                    return;
                }
                let data: IData = new IData(self.newStartDate(), self.beginStartDate(), self.dataSource.check, self.dataSource.mode, self.copyDataFlag());
                setShared('CMM018I_DATA', data);
                console.log(data);
                nts.uk.ui.windows.close(); 
            }
            
            /**
             * close dialog and do nothing
             */
            closeDialog(): void {
                $("#startDateInput").ntsError('clear');
                nts.uk.ui.windows.close();   
            }

        }
        interface IData_Param{
            /** name */
            name?: string
            /**開始日*/
            startDate: string;
            /**開始日 Old*/
            startDateOld?: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /** まとめて設定モード(0) - 申請個別設定モード(1)*/
            mode: number;
        }
        class IData{
            /**開始日*/
            startDate: string;
            /**開始日 Old*/
            startDateOld: string;
            /**check 申請承認の種類区分: 会社(1)　－　職場(2)　－　社員(3)*/
            check: number;
            /** まとめて設定モード(0) - 申請個別設定モード(1)*/
            mode: number;
            /** 履歴から引き継ぐか、初めから作成するかを選択する*/
            copyDataFlag: boolean;
            constructor(startDate: string,
                startDateOld: string,
                check: number,
                mode: number,
                copyDataFlag: boolean){
                    this.startDate = startDate;
                    this.startDateOld = startDateOld;
                    this.check = check;
                    this.mode = mode;
                    this.copyDataFlag = copyDataFlag;
            }
        }
    }
}