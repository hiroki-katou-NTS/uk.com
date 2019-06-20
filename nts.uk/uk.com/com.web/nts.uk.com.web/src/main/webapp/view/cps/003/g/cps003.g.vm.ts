module cps003.g.vm {
    import block = nts.uk.ui.block;
    import getShared = nts.uk.ui.windows.getShared;
    import setShared = nts.uk.ui.windows.setShared;
    



    export class ViewModel {
        data: KnockoutObservableArray<any>;
        constructor() {
            var self = this;
            self.data = ko.observable([]);
        }
        start() {
            let self = this;
            var dfd = $.Deferred();
            let paramA = getShared("CPS003G_ERROR_LIST"), paramC: GridDtoError = getShared("CPS003G_ERROR_LIST");
            self.data(_.map(paramA, a => {
                return {
                    empCd: a.empCd, empName: a.empName,
                    employeeId: a.employeeId, errorType: a.errorType == 0 ? nts.uk.resource.getText("CPS003_127") : nts.uk.resource.getText("CPS003_128"),
                    isDisplayRegister: a.isDisplayRegister, itemName: a.itemName, message: a.message, no: a.no
                }
            }));
            $("#grid2").igGrid({
                autoGenerateColumns: true,
                dataSource: self.data(),
                columns: [
                    { headerText: nts.uk.resource.getText("CPS003_100"), key: "empCd", dataType: "string" ,width: "100px"},
                    { headerText: nts.uk.resource.getText("CPS003_101"), key: "empName", dataType: "string",width: "100px" },
                    { headerText: nts.uk.resource.getText("CPS003_102"), key: "no", dataType: "string",width: "50px" },
                    { headerText: nts.uk.resource.getText("CPS003_103"), key: "isDisplayRegister", dataType: "boolean",width: "50px" , formatter: function(v) { return v ? '〇' : 'X' }  },
                    { headerText: nts.uk.resource.getText("CPS003_104"), key: "errorType", dataType: "string",width: "50px" },
                    { headerText: nts.uk.resource.getText("CPS003_105"), key: "itemName", dataType: "string",width: "250px" },
                    { headerText: nts.uk.resource.getText("CPS003_106"), key: "message", dataType: "string",width: "500px" },
                     { headerText: nts.uk.resource.getText("CPS003_106"), key: "employeeId", dataType: "string",  hidden: true }
                ],
                features: [{
                    name: "Paging",
                    type: "local",
                    pageSize: 5
                }],
            });
            dfd.resolve();
            return dfd.promise();
        }
        close() {
            nts.uk.ui.windows.close();
        }
        exportFile() {
            let self = this,
                dataGroup =  _.groupBy(self.data(), "employeeId"),
                result = [],
                isDisplayE1_006 = self.data().length > 0 ? self.data()[0].isDisplayRegister: false;
            _.each(dataGroup, c => {
                let em = { employeeId: c[0].employeeId, employeeCd: c[0].empCd, employeeName: c[0].empName, order: c[0].no, errorLst: [] };
                _.each(c, i => {
                    // 0 - ERROR, 1 - WARNING
                    let item = { itemName: i.itemName, message: i.message, errorType: 0};
                        em.errorLst.push(item);
                });
                result.push(em);
            })
            let itemErrorLst = {isDisplayE1_006: isDisplayE1_006, errorEmployeeInfoLst: result};

            nts.uk.request.exportFile('com', '/person/matrix/report/print/error', itemErrorLst).done(data => { console.log(data); }).fail((mes) => {
            });
        }
    
    }

         export interface PersonMatrixErrorDataSource {
             isDisplayE1_006: boolean;
             errorEmployeeInfoLst: Array<ErrorWarningEmployeeInfoDataSource>;
         }
           
         export interface ErrorWarningEmployeeInfoDataSource {
            employeeId: string;
            employeeCd: string;
            employeeName: string;
            order: number;
            errorLst: Array<ErrorWarningInfoOfRowOrderDataSource>;
        }
        
         export interface ErrorWarningInfoOfRowOrderDataSource {
             itemName: string;
             errorType: number;
             message: string;
         }

}