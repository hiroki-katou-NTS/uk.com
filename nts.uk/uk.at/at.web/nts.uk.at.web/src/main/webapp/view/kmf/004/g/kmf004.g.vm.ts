module nts.uk.at.view.kmf004.g.viewmodel {
    import windows = nts.uk.ui.windows;
    import getShared = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        // list relationship A2_2
        lstRelationship: KnockoutObservableArray<GrantRelationship>;
        // grant relationship code
        grantRelationshipCode: KnockoutObservable<string>;
        // column in list
        gridListColumns: KnockoutObservableArray<any>;
        // selected code    
        selectedCode: KnockoutObservable<string>;
        // selected item in the list 
        selectedOption: KnockoutObservable<GrantRelationship>;
        // selected item in grant relationship
        grantSelected: KnockoutObservable<GrantRelationship>
        // binding to text box name A3_3
        selectedName: KnockoutObservable<string>;
        // binding to text box code A3_2         
        codeObject: KnockoutObservable<string>;
        // check new mode or not   
        check: KnockoutObservable<boolean>;
        // check update or insert
        checkUpdate: KnockoutObservable<boolean>;
        // check enable delete button
        checkDelete: KnockoutObservable<boolean>;
        // grant relationship day
        grantDay: KnockoutObservable<number>;
        // grant relationship hour
        morningHour: KnockoutObservable<number>;
        // check icon
        isAlreadySet: KnockoutObservable<boolean>;
        // check required
        checkRequired: KnockoutObservable<boolean> = ko.observable(false);
        constructor() {
            let self = this;
            self.gridListColumns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KMF004_7"), key: 'relationshipCode', width: 80 },
                { headerText: nts.uk.resource.getText("KMF004_8"), key: 'relationshipName', width: 180, formatter: _.escape }
            ]);
            self.gridListColumns.push({
                headerText: nts.uk.resource.getText("KMF004_129"), key: 'activeAlready', width: 70,
                formatter: function(val, record) {
                    if ("true" == val) {
                        return '<div style="text-align: center; max-height: 18px;"><i class="icon icon-78"></i></div>';
                    }
                    return '';
                }
            });
            self.lstRelationship = ko.observableArray([]);
            self.grantRelationshipCode = ko.observable("");
            self.selectedCode = ko.observable("");
            self.selectedName = ko.observable("");
            self.selectedOption = ko.observable(null);
            self.grantSelected = ko.observable(null);
            self.check = ko.observable(false);
            self.codeObject = ko.observable("");
            self.checkUpdate = ko.observable(true);
            self.checkDelete = ko.observable(true);
            self.isAlreadySet = ko.observable(false);
            self.grantDay = ko.observable(null);
            self.morningHour = ko.observable(null);
            self.selectedCode.subscribe((value) => {
                if (value) {
                    let foundItem = _.find(self.lstRelationship(), (item: GrantRelationship) => {
                        return item.relationshipCode == value;
                    });
                    self.checkUpdate(true);
                    self.checkDelete(true);
                    self.selectedOption(foundItem);
                    self.selectedName(self.selectedOption().relationshipName);
                    self.codeObject(self.selectedOption().relationshipCode);
                    self.grantRelationshipCode(self.codeObject());
                    self.check(false);
                    self.grantDay(null);
                    self.morningHour(null);
                    // bổ sung vì đã tối ưu hóa webservice thành 1
                    self.isAlreadySet(foundItem.activeAlready);
                    self.grantSelected(foundItem);
                    self.grantDay(foundItem.grantRelationshipDay);
                    self.morningHour(foundItem.morningHour);
                    self.checkRequired(false); 
                    if(self.morningHour() != null){
                        self.checkRequired(true);   
                    }
                    // #end
                    
                    nts.uk.ui.errors.clearAll();
                }
            });
        }

        /** get data to list **/
        getData(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            let specialHolidayCode = getShared("KMF004G_SPHD_CD");
            service.findAllGrantRelationship(specialHolidayCode).done((lstData: Array<viewmodel.GrantRelationship>) => {
                let sortedData = _.orderBy(lstData, ['relationshipCode'], ['asc']);
                self.lstRelationship(sortedData);
                dfd.resolve();               
            }).fail(function(error) {
                dfd.reject();
                alert(error.message);
            })
            return dfd.promise();
        }

        /** get data when start dialog **/
        startPage(): JQueryPromise<any> {
            nts.uk.ui.block.invisible();
            let self = this;
            let dfd = $.Deferred();
            let array = [];
            let list = [];
            self.getData().done(function() {
                if (self.lstRelationship().length == 0) {
                    nts.uk.ui.dialog.info({ messageId: "Msg_375" });
                    nts.uk.ui.windows.close();
                    self.checkDelete(false);
                }
                else {
                    self.selectedCode(self.lstRelationship()[0].relationshipCode);
                    self.grantRelationshipCode(self.selectedCode());
                }
                nts.uk.ui.block.clear();
                dfd.resolve();
            });
            return dfd.promise();
        }

        /** update or insert data when click button register **/
        register() {            
            let self = this;
            $("#inpDay").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;    
            }
            
            nts.uk.ui.block.invisible();
            let specialHolidayCode = getShared("KMF004G_SPHD_CD");
            let obj = new GrantRelationship(specialHolidayCode, self.selectedCode(), self.selectedName(), self.grantDay(), self.morningHour(), self.isAlreadySet());
            if(!nts.uk.ui.errors.hasError()){
                service.insert(obj).done(function() {
                    self.getData().done(function() {
                        self.isAlreadySet(true);
                        self.grantSelected(obj);
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                    });
                }).fail(function(error) {
                    if (error.messageId == 'Msg_372') {
                        $('#inpHour').addClass("error");
                        $('#inpHour').ntsError('set', { messageId: "Msg_372" });
                    }else{
                        alert(error.message);                            
                    }
                });
            }
            nts.uk.ui.block.clear();
        }

        /** remove item from list **/
        remove() {
            let self = this;
            let count = 0;
            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {
                service.remove(self.grantSelected()).done(function() {
                    self.isAlreadySet(false);
                    self.getData();
                    self.grantDay(null);
                    self.morningHour(null);
                    nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                })
            }).ifNo(() => {
            });
        }

        close() {
            nts.uk.ui.windows.close();
        }

    }
    export class GrantRelationship {
        specialHolidayCode: string;
        relationshipCode: string;
        relationshipName: string;
        grantRelationshipDay: number;
        morningHour: number;
        isAlreadySet: boolean;
        constructor(specialHolidayCode: string, relationshipCode: string, relationshipName: string, grantRelationshipDay: number, morningHour: number, isAlreadySet: boolean) {
            this.specialHolidayCode = specialHolidayCode;
            this.relationshipCode = relationshipCode;
            this.relationshipName = relationshipName;
            this.grantRelationshipDay = grantRelationshipDay;
            this.morningHour = morningHour;
            this.isAlreadySet = isAlreadySet;
        }
    }
}




