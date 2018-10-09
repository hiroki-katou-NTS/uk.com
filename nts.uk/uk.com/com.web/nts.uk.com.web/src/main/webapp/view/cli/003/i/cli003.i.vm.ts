module nts.uk.com.view.cli003.i {
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import alertError = nts.uk.ui.dialog.alertError;
    export module viewmodel {

        export class ScreenModel {
            listCode: KnockoutObservableArray<model.LogDisplaySetting>;
            recordType: KnockoutObservable<string>;
            tarGetDataType: KnockoutObservable<string>;
         
            constructor() {
                let self = this;
                self.recordType = ko.observable(getShared('recordType').toString());
                self.tarGetDataType = ko.observable(getShared('tarGetDataType').toString());        
                console.log(self.recordType());
                console.log(self.tarGetDataType());
                self.listCode = ko.observableArray([]);
                self.currentCode = ko.observable(null);
                self.columns = ko.observableArray([
                    { headerText: getText("CLI003_11"), prop: 'code', width: 100 },
                    { headerText: getText("CLI003_12"), prop: 'name', width: 233 }
                ]);
                self.getListLogDisplaySettingByRecordType();
                nts.uk.ui.windows.setShared("selectCancel", false);
            }

            /** Get list log by recordType */
            private getListLogDisplaySettingByRecordType() {
                let self = this;
                let dfd = $.Deferred();
                let paramInputLog={
                    recordType:self.recordType(),
                    targetDataType:self.tarGetDataType()
                    }
                service.getLogDisplaySettingByRecordType(paramInputLog).done(function(data: any) {
                    console.log(data);
                    if (data && data.length > 0) {
                        data = _.orderBy(data, ['code'], ['asc', 'asc']);
                        for (let i = 0; i < data.length; i++) {
                            var logDisplaySet = data[i];
                            self.listCode.push(new model.LogDisplaySetting(logDisplaySet.logSetId, logDisplaySet.cid, logDisplaySet.code, logDisplaySet.name,
                                logDisplaySet.dataType, logDisplaySet.recordType));

                        }

                        self.selectFirstItem();
                    } else {

                        alertError({ messageId: "Msg_1215" });
                        //nts.uk.ui.windows.close();
                    }       
                }).fail(function(res: any) {
                    dfd.reject();
                    nts.uk.ui.dialog.alertError(res.message).then(() => {
                        nts.uk.ui.block.clear();
                    });
                });
                return dfd.promise();
            }

            /** Select first item */
            selectFirstItem() {
                let self = this;
                if (self.listCode().length > 0) {
                    self.currentCode(self.listCode()[0].code);
                }
            }
            /** btn decision*/
            decision() {
                let self = this;
                nts.uk.ui.windows.setShared("datacli003", self.currentCode());
                if(self.currentCode()){
                      nts.uk.ui.windows.close();
                    }
              
            }

            /** btn cancel*/
            cancel() {
                nts.uk.ui.windows.setShared("selectCancel", true);
                nts.uk.ui.windows.close();
            }


        }//end viewmodel


        //module model
        export module model {

            export interface ILogDisplaySetting {
                logSetId: string;
                cid: string;
                code: string;
                name: string;
                dataType: string;
                recordType: number;
            }

            /** class LogDisplaySetting */
            export class LogDisplaySetting {
                logSetId: string;
                cid: string;
                code: string;
                name: string;
                dataType: string;
                recordType: number;
                constructor(logSetId: string, cid: string,
                    code: string, name: string,
                    dataType: string, recordType: number) {
                    this.logSetId = logSetId;
                    this.cid = cid;
                    this.code = code;
                    this.name = name;
                    this.dataType = dataType;
                    this.recordType = recordType;
                }
            }//end class LogDisplaySetting


        }//end module model
    }
    }//end module