module nts.uk.com.view.cmm040.b.viewmodel {
    import block = nts.uk.ui.block;
    import util = nts.uk.util;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {
        selectCode: KnockoutObservable<any>;
        workLocationList: KnockoutObservableArray<any>;
        valueB3_4_ipaddress1: KnockoutObservable<string>;
        valueB3_6_ipaddress2: KnockoutObservable<string>;
        valueB3_8_ipaddress3: KnockoutObservable<string>;
        valueB3_10_ipaddress4: KnockoutObservable<string>;
        valueB3_12: KnockoutObservable<string> = ko.observable("");
        workLocationCode: KnockoutObservable<string> = nts.uk.ui.windows.getShared('CMM040B').workLocationCD;
        workLocationName: KnockoutObservable<string> = nts.uk.ui.windows.getShared('CMM040B').workLocationName;

        //workLocationName:
        constructor() {
            var self = this;
            this.selectCode = ko.observable(null);
            self.valueB3_4_ipaddress1 = ko.observable('');
            self.valueB3_6_ipaddress2 = ko.observable('');
            self.valueB3_8_ipaddress3 = ko.observable('');
            self.valueB3_10_ipaddress4 = ko.observable('');
            this.workLocationList = ko.observableArray([]);
            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL010_2"), prop: 'workLocationName', width: 290 }
            ]);

            self.selectCode.subscribe(function(value) {
                 if (value == null ) return;
                self.valueB3_4_ipaddress1(value.split(".")[0]);
                self.valueB3_6_ipaddress2(value.split(".")[1]);
                self.valueB3_8_ipaddress3(value.split(".")[2]);
                self.valueB3_10_ipaddress4(value.split(".")[3]);
                $("#target").focus();
            });
        }


        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            let workLocationCode = self.workLocationCode;

            service.getDataStart(workLocationCode).done(function(result) {
                console.log(result);
                if (result.length > 0) {

                    let data = result[0];
                    let datas = [];
                    for (i = 0; i < result.length; i++) {
                        let ip = result[i].net1 + "." + result[i].net2 + "." + result[i].host1 + "." + result[i].host2
                        datas.push(new WorkLocation(ip, ip));

                    }
                    self.workLocationList(datas);
                    self.selectCode(data.net1 + "." + data.net2 + "." + data.host1 + "." + data.host2);
                    self.valueB3_4_ipaddress1(data.net1);
                    self.valueB3_6_ipaddress2(data.net2);
                    self.valueB3_8_ipaddress3(data.host1);
                    self.valueB3_10_ipaddress4(data.host2);
                }
                dfd.resolve();
            }).fail(function(error) {
                dfd.fail();
                alert(error.message);
            });

            return dfd.promise();
        }

        cancel_Dialog(): any {
            let self = this;
            nts.uk.ui.windows.close();
        }
        

        save(): any {
            var self = this;
            let i;
            if (self.valueB3_10_ipaddress4() >= self.valueB3_12()) {
                i = self.valueB3_10_ipaddress4();
            }
            else {
                i = self.valueB3_12();
            }    
            let param =
                {   
                
                    workLocationCode: self.workLocationCode,
                    net1: self.valueB3_4_ipaddress1(),
                    net2: self.valueB3_6_ipaddress2(),
                    host1: self.valueB3_8_ipaddress3(),
                    host2: self.valueB3_10_ipaddress4(),
                    ipEnd: i
                }
            service.update(param).done((result) => {
                console.log(result);
                if (result.length == 0) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_15" }).then(function() {
                        $('#companyName').focus();
                    });

                } else {
                    for (let i = 0; i < result.length; i++) {
                        $('#left-content').ntsError('set', { messageId: 'Msg_1994', messageParams: [result[i].net1, result[i].net1, result[i].host1, result[i].host2] });
                    }

                }

            }).fail((res: any) => {
                nts.uk.ui.dialog.alert({ messageId: res.messageId });
            }).always(() => {
                block.clear();
            });


        }
        deleteData(): any {
            var self = this;
            let param = {
                workLocationCode: self.workLocationCode,
                net1: Number(self.valueB3_4_ipaddress1()),
                net2: Number(self.valueB3_6_ipaddress2()),
                host1: Number(self.valueB3_8_ipaddress3()),
                host2: Number(self.valueB3_10_ipaddress4())
            }
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.deleteData(param).done(() => {
                    self.startPage().done(() => {

                    });

                }).fail((res: any) => {
                    nts.uk.ui.dialog.alert({ messageId: res.messageId });
                }).always(() => {
                    block.clear();
                });
            });
        }
        
        newMode():any{
            var self = this;
            self.selectCode(null);
            self.valueB3_4_ipaddress1('');
            self.valueB3_6_ipaddress2('');
            self.valueB3_8_ipaddress3('');
            self.valueB3_10_ipaddress4('');
            self.valueB3_12('');
        
        
        
        }
    }
    export class WorkLocation {
        workLocationCD: string;
        workLocationName: string;
        constructor(workLocationCD: string, workLocationName: string) {
            this.workLocationCD = workLocationCD;
            this.workLocationName = workLocationName
        }
    }
}