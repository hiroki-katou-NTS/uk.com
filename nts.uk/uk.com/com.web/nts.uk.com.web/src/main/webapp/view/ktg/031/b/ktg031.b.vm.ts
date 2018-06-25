module nts.uk.at.view.ktg031.b.viewmodel {
    import getText = nts.uk.resource.getText;
    export class ScreenModel {
        listDetail: KnockoutObservableArray<TopPageAlarmDetailDto> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<number> = ko.observable();
        constructor() {
            var self = this;
            self.columns = ko.observableArray([
                { headerText: '', key: 'serialNo', width: 50 },
                { headerText: getText('KTG031_10'), key: 'employeeCode', width: 100 },
                { headerText: getText('KTG031_11'), key: 'employeeName', width: 110 },
                { headerText: getText('KTG031_12'), key: 'processingName', width: 150 },
                { headerText: getText('KTG031_13'), key: 'errorMessage', width: 190 },
            ]);
        }   

        startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            let ParamKtg031 = nts.uk.ui.windows.getShared("ktg031A");
            service.getDetail(ParamKtg031).done((listOutput: Array<ITopPageAlarmDetailDto>) => {
                console.log(listOutput);
                self.listDetail(_.map(listOutput, acc => {
                    return new TopPageAlarmDetailDto(acc);
                }));
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        closeDialog(){
            nts.uk.ui.windows.close();
        }
    }

    export interface ITopPageAlarmDetailDto {
        /** 社員コード */
        employeeCode: string;

        /** 連番 */
        serialNo: number;

        /** 社員名 */
        employeeName: string;

        /** エラーメッセージ */
        errorMessage: string;

        /** 処理名 */
        processingName: string;
    }

    export class TopPageAlarmDetailDto {
        /** 社員コード */
        employeeCode: string;

        /** 連番 */
        serialNo: number;

        /** 社員名 */
        employeeName: string;

        /** エラーメッセージ */
        errorMessage: string;

        /** 処理名 */
        processingName: string;
        constructor(param: ITopPageAlarmDetailDto) {
            let self = this;
            self.employeeCode = param.employeeCode;
            self.serialNo = param.serialNo;
            self.employeeName = param.employeeName;
            self.errorMessage = param.errorMessage;
            self.processingName = param.processingName;
        }
    }

}
