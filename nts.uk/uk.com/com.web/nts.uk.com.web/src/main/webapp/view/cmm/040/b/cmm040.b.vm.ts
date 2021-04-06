module nts.uk.com.view.cmm040.b.viewmodel {
    import block = nts.uk.ui.block;
    import util = nts.uk.util;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {
        selectCode: KnockoutObservable<any>;
        workLocationList: KnockoutObservableArray<any>;
        valueB3_4_ipaddress1: KnockoutObservable<number>;
        valueB3_6_ipaddress2: KnockoutObservable<number>;
        valueB3_8_ipaddress3: KnockoutObservable<number>;
        valueB3_10_ipaddress4: KnockoutObservable<number>;
        valueB3_12: KnockoutObservable<number> = ko.observable(null);
        workLocationCode: KnockoutObservable<string> = nts.uk.ui.windows.getShared('CMM040B').workLocationCD;
        workLocationName: KnockoutObservable<string> = nts.uk.ui.windows.getShared('CMM040B').workLocationName;

        //workLocationName:
        isCreate: KnockoutObservable<boolean>;
        constructor() {
            var self = this;
            this.selectCode = ko.observable(null);
            self.valueB3_4_ipaddress1 = ko.observable(null);
            self.valueB3_6_ipaddress2 = ko.observable(null);
            self.valueB3_8_ipaddress3 = ko.observable(null);
            self.valueB3_10_ipaddress4 = ko.observable(null);
            this.workLocationList = ko.observableArray([]);
            self.isCreate = ko.observable(null);
            this.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KDL010_2"), prop: 'workLocationName', width: 290 }
            ]);

            self.selectCode.subscribe(function(value) {
                if (value == null) return;
                self.isCreate(false);
                self.valueB3_4_ipaddress1(value.split(".")[0]);
                self.valueB3_6_ipaddress2(value.split(".")[1]);
                self.valueB3_8_ipaddress3(value.split(".")[2]);
                self.valueB3_10_ipaddress4(value.split(".")[3]);
                $("#target").focus();
                errors.clearAll();
                
            });
            
            self.valueB3_4_ipaddress1.subscribe(function(value) {
                if(value == null) return;
                if (value.toString() == "" ||  (value > 255) || (value < 0)) {
                    $('#target').ntsError('set', {messageId:"Msg_2153"});
                }
                });
             self.valueB3_6_ipaddress2.subscribe(function(value) {
                 if(value == null) return;
                if (value.toString() == "" ||  (value > 255) || (value < 0)) {
                    $('#validateB3_6').ntsError('set', {messageId:"Msg_2153"});
                }
                });
             self.valueB3_8_ipaddress3.subscribe(function(value) {
                 if(value == null) return;
                if (value.toString() == "" ||  (value > 255) || (value < 0)) {
                    $('#validateB3_8').ntsError('set', {messageId:"Msg_2153"});
                }
                });
             self.valueB3_10_ipaddress4.subscribe(function(value) {
                 if(value == null) return;
                if (value.toString() == "" ||  (value > 255) || (value < 0)) {
                    $('#validateB3_10').ntsError('set', {messageId:"Msg_2153"});
                }
                });
             self.valueB3_12.subscribe(function(value) {
                 if(value == null) return;
                if (value.toString() == "" ||  (value > 255) || (value < 0)) {
                    $('#validateB3_12').ntsError('set', {messageId:"Msg_2153"});
                }
                });
        }


        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            let workLocationCode = self.workLocationCode;

            service.getDataStart(workLocationCode).done(function(result) {
                if (result.length > 0) {
                    let data = result[0];
                    let datas = [];
                    for (i = 0; i < result.length; i++) {
                        let ip = result[i].net1 + "." + result[i].net2 + "." + result[i].host1 + "." + result[i].host2
                        datas.push(new WorkLocation(ip, ip));

                    }
                    self.workLocationList(datas);
                    self.selectCode(self.valueB3_4_ipaddress1() + "." + self.valueB3_6_ipaddress2() + "." + self.valueB3_8_ipaddress3() + "." + self.valueB3_10_ipaddress4());
                    
                    self.valueB3_4_ipaddress1(self.valueB3_4_ipaddress1());
                    self.valueB3_6_ipaddress2( self.valueB3_6_ipaddress2());
                    self.valueB3_8_ipaddress3(self.valueB3_8_ipaddress3());
                    self.valueB3_10_ipaddress4(self.valueB3_10_ipaddress4());
                }
                else {
                    self.isCreate(true);
                    $("#target").focus();
                     self.valueB3_4_ipaddress1(null);
                    self.valueB3_6_ipaddress2(null);
                    self.valueB3_8_ipaddress3(null);
                    self.valueB3_10_ipaddress4(null);
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
        
          private findByIndex(index: number) {
            let self = this
            let data = _.nth(self.workLocationList(), index);
            if (data !== undefined) {
                //  self.selectedWorkLocation()
                self.selectCode(data.selectCode);
            }
            else {
              //  self.selectedWorkLocation(null);
            }
        }
        
        

          save(): any {
              $(".nts-input").trigger("validate");
              if (!$(".nts-input").ntsError("hasError")) {
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
                      if (result.length == 0) {
                          nts.uk.ui.dialog.info({ messageId: "Msg_15" })
                          self.startPage().done(() => {
                              self.selectCode(self.valueB3_4_ipaddress1() + "." + self.valueB3_6_ipaddress2() + "." + self.valueB3_8_ipaddress3() + "." + self.valueB3_10_ipaddress4());
                              self.isCreate(false);
                          });


                      } else {

                          for (let i = 0; i < result.length; i++) {
                              $('#left-content').ntsError('set', { messageId: 'Msg_1994', messageParams: [result[i].net1, result[i].net1, result[i].host1, result[i].host2] });
                              let p = nts.uk.ui.errors.errorsViewModel();
                              p.option().show.subscribe(v => {
                                  if (v == false) {
                                      nts.uk.ui.errors.clearAll();
                                  }
                              });
                          }
                      }
                  }).fail((res: any) => {
                      nts.uk.ui.dialog.alert({ messageId: res.messageId });
                  }).always(() => {
                      block.clear();
                  });
              }
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
                    let index = _.findIndex(self.workLocationList(), ['workLocationCD', self.selectCode()]);
                    index = _.min([self.workLocationList().length - 2, index]);
                    self.startPage().done(() => {
                         self.findByIndex(index);
                           nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });

                }).fail((res: any) => {
                    nts.uk.ui.dialog.alert({ messageId: res.messageId });
                }).always(() => {
                    block.clear();
                });
            });
        }

        newMode(): any {
            var self = this;
            errors.clearAll();
            self.selectCode(null);
            self.valueB3_4_ipaddress1(null);
            self.valueB3_6_ipaddress2(null);
            self.valueB3_8_ipaddress3(null);
            self.valueB3_10_ipaddress4(null);
            self.valueB3_12(null);
            self.isCreate(true);
            $("#target").focus();



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