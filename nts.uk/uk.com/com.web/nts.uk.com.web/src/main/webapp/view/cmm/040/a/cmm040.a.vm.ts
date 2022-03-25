module nts.uk.com.view.cmm040.a.viewmodel {
    import block = nts.uk.ui.block;
    import util = nts.uk.util;
    import errors = nts.uk.ui.errors;
    export class ScreenModel {

        tabs: KnockoutObservableArray<nts.uk.ui.NtsTabPanelModel>;
        selectedTab: KnockoutObservable<string>;
        workPlacesList: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedWorkLocation: KnockoutObservable<string> = ko.observable(null);
        //selectedWorkLocation: KnockoutObservable<any>;
        //
        valueA5_2: KnockoutObservable<string> = ko.observable('');
        //
        currentBonusPaySetting: KnockoutObservable<BonusPaySetting>;
        //
        itemList: KnockoutObservableArray<ItemModel> = ko.observableArray([]);
        radius: KnockoutObservable<number> = ko.observable('');
        //
        // items: KnockoutObservableArray<ItemModel>;
        //
        workLocationCD: KnockoutObservable<string>;
        workLocationName: KnockoutObservable<string>;
        //
        latitude: KnockoutObservable<number> = ko.observable(null);
        longitude: KnockoutObservable<number> = ko.observable(null);
        //
        isCreate: KnockoutObservable<boolean>;

        workPlaceID: KnockoutObservable<string> = ko.observable('');

        listWorkPlaceIDs: any = [];
        LoginCompanyId: any = '';

        loginInfo: any;
        output: any;
        items: any = [];
        listSelectWorkplaceID: any = [];

        cdl008data: KnockoutObservableArray<any> = ko.observableArray([]);

        radiusEnum: KnockoutObservableArray<Enum>;

        enableA53: KnockoutObservable<boolean>;

        option: any;
        listIpCancel: KnockoutObservableArray<any> = ko.observableArray([]);

        workplaceCode: KnockoutObservable<String> = ko.observable('');
        workplaceDisplayName: KnockoutObservable<String> = ko.observable('');
        selectAreaCombobox: KnockoutObservableArray<any> = ko.observableArray([]);
        selectAreaCode: KnockoutObservable<String> = ko.observable('');

        changeWorkPlaceID: String = '';
        // Cần đạt như này để lưu đúng giá trị vào database.
        defautValueLocation: String = '';
        DEFAUT_VALUE_RADIUS = 9999;

        constructor() {
            let self = this;

            self.option = new nts.uk.ui.option.NumberEditorOption({
                numberGroup: true,
                decimallength: 6,
                textalign: "left"
            }
            )

            self.workLocationCD = ko.observable('');
            self.workLocationName = ko.observable('');
            self.currentBonusPaySetting = ko.observable(new BonusPaySetting('', '', ''));
            //
            self.isCreate = ko.observable(null);
            self.enableA53 = ko.observable(false);
            //
            self.radiusEnum = ko.observableArray([]);
            self.tabs = ko.observableArray([
                { id: 'tab-1', title: nts.uk.resource.getText("CMM040_9"), content: '.tab-content-1', enable: ko.observable(true), visible: ko.observable(true) },
                { id: 'tab-2', title: nts.uk.resource.getText("CMM040_10"), content: '.tab-content-2', enable: ko.observable(true), visible: ko.observable(true) }
            ]);
            self.selectedTab = ko.observable('tab-1');
            self.selectedWorkLocation.subscribe(function (value) {
                //if (value == null || value == self.workLocationCD()) return;
                if (value == null || value == "") {

                    self.newMode();
                    return;

                };


                // if (value == null) return;
                self.items = [];
                self.selectWorkLocation(value);
                self.listIpCancel([]);

            });
            self.valueA5_2.subscribe(function (value) {
                if (value == "") {
                    self.enableA53(false);
                }
                else {
                    self.enableA53(true);
                }
            });

            self.longitude.subscribe(function (value) {
                
                setTimeout(() => self.validate(), 100);
                
                if (!value || !value.toString()) {
                    return;
                }
                
               
                    
                setTimeout(() => {
                    if ((value > 180) || (value < -180)) {
                        let temp = _.find(nts.uk.ui.errors.getErrorList(), function(o) { return o.errorCode == "Msg_2161"; });
                        if (temp == null) {
                            $('#validatelong').ntsError('set', { messageId: "Msg_2162" })
                        }
                    }
                }, 100);

                
            });

            
            self.latitude.subscribe(function (value) {
                
                setTimeout(() => self.validate(), 100);
                
                if (!value || !value.toString()) {
                    return;
                }
                
                
                setTimeout(() => {
                    if ((value > 90) || (value < -90)) {
                        let temp = _.find(nts.uk.ui.errors.getErrorList(), function(o) { return o.errorCode == "Msg_2161"; });
                        if (temp == null) {
                            $('#validatelat').ntsError('set', { messageId: "Msg_2162" })
                        }
                    }
                }, 100);
                
            });
            
            self.radius.subscribe((value) => {
                
                setTimeout(() => self.validate(), 100);
                
                if (value != 9999) {
                    $('#combo-box .ui-igcombo').ntsError('clear');
                }
            });

        }
        
        validate() {
            let self = this;
            $(".nts-input").ntsError("clear");
            $(".nts-input").trigger("validate");
            $('#combo-box .ui-igcombo').ntsError('clear');
            if (( self.isNumberic(self.latitude()) || self.isNumberic(self.longitude())) && self.radius() == 9999) {
                $('#combo-box .ui-igcombo').ntsError('set', { messageId: "MsgB_1" ,messageParams:[nts.uk.resource.getText("CMM040_39")] });
                return;
            }
        }

        startPage(): JQueryPromise<any> {
            var self = this;

            var dfd = $.Deferred();
            this.loadRadiusEnums().done(res => {
                console.log("getEnum");
            });
            this.reloadData().done(function () {
                setTimeout(function () {
                    let datas = _.orderBy(self.items, ['companyCode'], ['asc']);
                    dfd.resolve();
                    self.selectWorkLocation(ko.unwrap(self.workLocationCD));
                }, 100);
            });

            return dfd.promise();
        }
        
        latitudeRequired() {
            let self = this;
            return self.radius() != 9999 || self.isNumberic(self.longitude());

        };
        
        isNumberic(value){
            return !isNaN(parseFloat(value)) && isFinite(value);
        }
        
        longitudeRequired() {
            let self = this;
            return self.radius() != 9999 || self.isNumberic(self.latitude());
        }
        
        

        setDataWorkPlace(data: any) {
            const self = this;
            if (data) {
                if (data.listCidAndWorkplaceInfo != null && data.listCidAndWorkplaceInfo.length > 0) {
                    if (data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport != null && data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport.length > 0) {
                        self.workplaceCode(data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport[0].workplaceCode);
                        self.workplaceDisplayName(data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport[0].workplaceDisplayName);

                        self.listSelectWorkplaceID = [];
                        self.listWorkPlaceIDs = [];
                        self.listWorkPlaceIDs.push({ companyId: self.LoginCompanyId, workpalceId: data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport[0].workplaceId });
                        self.listSelectWorkplaceID.push(data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport[0].workplaceId);
                        self.changeWorkPlaceID = data.listCidAndWorkplaceInfo[0].listWorkplaceInfoImport[0].workplaceId;
                    } else {
                        self.listSelectWorkplaceID = [];
                        self.listWorkPlaceIDs = [];

                        self.workplaceCode('');
                        self.workplaceDisplayName('');
                        self.changeWorkPlaceID = '';
                    }
                } else {
                    self.listSelectWorkplaceID = [];
                    self.listWorkPlaceIDs = [];

                    self.workplaceCode('');
                    self.workplaceDisplayName('');
                    self.changeWorkPlaceID = '';
                }
            } else {
                self.workplaceCode(ko.unwrap(self.workplaceCode));
                //self.workplaceDisplayName(ko.unwrap(self.workplaceCode));
                self.workplaceDisplayName(ko.unwrap(self.workplaceDisplayName));
            }
        }

        //tab3
        private reloadData(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            /** Get list TitleMenu*/
            service.getDataStart().done(function (data) {
                self.workPlacesList(data.listWorkLocationDto);
                self.LoginCompanyId = data.companyId;
                if (data.listWorkLocationDto.length) {
                    let i;
                    let listCidAndWorkplace = [];
                    let listWorkplace = [];
                    //let listWorkplaceS = data.listWorkLocationDto;
                    if (self.workLocationCD() == "") {

                        let result = data.listWorkLocationDto[0];
                        self.workLocationCD(result.workLocationCD);
                        self.selectedWorkLocation(self.workLocationCD());
                        self.workLocationName(result.workLocationName);
                        self.radius(result.radius == null ? 9999 : result.radius);
                        self.latitude(result.latitude);
                        self.longitude(result.longitude);
                        self.isCreate(false);
                        self.selectAreaCode(result.regionCode);
                    }
                    //
                    let listWorkplaceS = _.filter(data.listWorkLocationDto, function (o) { return o.workLocationCD == self.workLocationCD() });

                    for (i = 0; i < listWorkplaceS.length; i++) {
                        if (listWorkplaceS[i].workplace != null) {
                            for (j = 0; j < listWorkplaceS[i].workplace.length; j++) {
                                listWorkplace.push({
                                    companyId: listWorkplaceS[i].listWorkplace[j].companyId,
                                    workpalceId: listWorkplaceS[i].listWorkplace[j].workpalceId
                                }
                                );
                            }
                        }
                    }
                    service.getWorkPlace({ listWorkplace: listWorkplace, workLocationCD: ko.unwrap(self.workLocationCD) }).done(function (data) {
                        self.loginInfo = data;
                        let list = [];
                        let datagrid = [];

                        self.setDataWorkPlace(data);

                        for (i = 0; i < data.listCompany.length; i++) {

                            let list1 = _.filter(data.listCidAndWorkplaceInfo, function (o) { return o.companyId == data.listCompany[i].companyId; });
                            let workPlaceCode = '';
                            let workPlaceName = '';

                            if (list1.length > 0) {
                                for (j = 0; j < list1[0].listWorkplaceInfoImport.length; j++) {
                                    workPlaceCode += list1[0].listWorkplaceInfoImport[j].workplaceCode + '</br>';
                                    workPlaceName += list1[0].listWorkplaceInfoImport[j].workplaceDisplayName + '</br>';
                                }
                            }
                            datagrid.push(new GridItem(data.listCompany[i].companyCode, data.listCompany[i].companyName, workPlaceCode, workPlaceName));
                        }
                        self.items = datagrid;
                        
                    });
                } else {
                    self.selectWorkLocation(null);
                    self.isCreate(true);
                    let listWorkplace = [{ companyId: __viewContext.user.companyId, workpalceId: '' }];
                    service.getWorkPlace({ listWorkplace: listWorkplace, workLocationCD: ko.unwrap(self.workLocationCD) }).done(function (data) {

                        self.setDataWorkPlace(data);

                        self.loginInfo = data;
                        let list = [];
                        let datagrid = [];
                        for (i = 0; i < data.listCompany.length; i++) {

                            let list1 = _.filter(data.listCidAndWorkplaceInfo, function (o) { return o.companyId == data.listCompany[i].companyId; });
                            let workPlaceCode = '';
                            let workPlaceName = '';

                            if (list1.length > 0) {
                                for (j = 0; j < list1[0].listWorkplaceInfoImport.length; j++) {
                                    workPlaceCode += list1[0].listWorkplaceInfoImport[j].workplaceCode + '</br>';
                                    workPlaceName += list1[0].listWorkplaceInfoImport[j].workplaceDisplayName + '</br>';
                                }
                            }
                            datagrid.push(new GridItem(data.listCompany[i].companyCode, data.listCompany[i].companyName, workPlaceCode, workPlaceName));
                        }
                        self.items = datagrid;
                        let checkdata = _.filter(self.items, function (o) { return o.companyCode == __viewContext.user.companyCode });
                        if (checkdata.length == 0) {
                            self.items.push(new GridItem(data.listCompany[0].companyCode, data.listCompany[0].companyName, '', ''));
                        }
                    });
                    $("#focus").focus();
                }
                
                service.getInfoOnTimeDifference().done((result) => {
                    let comboItems = _.chain(result)
                        .map(({code, name, regionalTimeDifference}) => {
                            let sign = regionalTimeDifference == 0 ? '±' : regionalTimeDifference > 0 ? '＋' : '－';
                            return { code, name, regionalTimeDifference, text: nts.uk.resource.getText('CMM040_46', [sign + nts.uk.time.format.byId("Time_Short_HM", Math.abs(regionalTimeDifference))]) };
                        })
                        .orderBy(['code'])
                        .value();
                    self.selectAreaCombobox(comboItems);
                    if (self.isCreate()) {
                        self.setDefaultSelectAreaCode();
                    }
                    dfd.resolve();
                }).fail(function(error) {
                    dfd.fail();
                    nts.uk.ui.dialog.alertError(error.message);
                });
                
            }).fail(function (error) {
                dfd.fail();
                nts.uk.ui.dialog.alertError(error.message);
            });

            return dfd.promise();
        }
        
        private setDefaultSelectAreaCode() {
            let self = this;
            if (self.selectAreaCombobox().length) {
                let codeOfZero = _.get(_.find(self.selectAreaCombobox(), ['regionalTimeDifference', 0]), 'code');
                if (codeOfZero) {
                    self.selectAreaCode(codeOfZero);
                } else {
                    let [first] = self.selectAreaCombobox();
                    self.selectAreaCode(first.code);
                }
            }
        }

        private selectWorkLocation(workLocationCD: string) {
            errors.clearAll();
            let self = this;
            self.isCreate(false);
            self.valueA5_2('');
            let data = _.find(self.workPlacesList(), ['workLocationCD', workLocationCD]);
            // $("#grid2").remove();
            // $("#grid").append("<table id='grid2'></table>");
            self.listWorkPlaceIDs = [];
            if (workLocationCD != null && workLocationCD != "") {
                self.isCreate(false);
                self.workLocationCD(data.workLocationCD);
                self.workLocationName(data.workLocationName);
                self.radius(data.radius == null ? 9999 : data.radius);
                self.latitude(data.latitude);
                self.longitude(data.longitude);
                self.selectAreaCode(data.regionCode);
                let listWorkplace = [];
                let list1 = _.find(self.workPlacesList(), function (o) { return o.workLocationCD == workLocationCD; });
                if (list1 != null && list1.workplace != null && list1.workplace.length > 0) {
                    for (let j = 0; j < list1.workplace.length; j++) {
                        listWorkplace.push({
                            companyId: list1.listWorkplace[j].companyId,
                            workpalceId: list1.listWorkplace[j].workpalceId
                        }
                        );
                    }
                }
                else {
                    listWorkplace.push({
                        companyId: __viewContext.user.companyId,
                        workpalceId: ''
                    });
                }
                if (listWorkplace.length > 0) {

                    let getWplLogin = _.filter(listWorkplace, function (o) { return o.companyId == self.LoginCompanyId });
                    for (let i = 0; i < getWplLogin.length; i++) {
                        self.listWorkPlaceIDs.push({ companyId: self.LoginCompanyId, workpalceId: getWplLogin[i].workpalceId });
                    }
                    service.getWorkPlace({ listWorkplace: listWorkplace, workLocationCD: ko.unwrap(self.workLocationCD) }).done(function (data) {

                        self.setDataWorkPlace(data);

                        self.loginInfo = data;
                        let list = [];
                        let datagrid = [];
                        self.listSelectWorkplaceID = [];
                        if (data.listCidAndWorkplaceInfo.length > 0) {
                            for (let i = 0; i < data.listCidAndWorkplaceInfo.length; i++) {
                                if (data.listCidAndWorkplaceInfo[i].companyId == __viewContext.user.companyId) {
                                    for (let k = 0; k < data.listCidAndWorkplaceInfo[i].listWorkplaceInfoImport.length; k++) {
                                        self.listSelectWorkplaceID.push(data.listCidAndWorkplaceInfo[i].listWorkplaceInfoImport[k].workplaceId);
                                    }
                                    break;
                                }
                            }
                        }
                        for (let i = 0; i < data.listCompany.length; i++) {

                            let list1 = _.filter(data.listCidAndWorkplaceInfo, function (o) { return o.companyId == data.listCompany[i].companyId; });
                            let workPlaceCode = '';
                            let workPlaceName = '';

                            if (list1.length > 0) {
                                for (let j = 0; j < list1[0].listWorkplaceInfoImport.length; j++) {
                                    workPlaceCode += list1[0].listWorkplaceInfoImport[j].workplaceCode + '</br>';
                                    workPlaceName += list1[0].listWorkplaceInfoImport[j].workplaceDisplayName + '</br>';
                                }
                            }
                            datagrid.push(new GridItem(data.listCompany[i].companyCode, data.listCompany[i].companyName, workPlaceCode, workPlaceName));
                        }
                        let checkdata = _.filter(datagrid, function (o) { return o.companyCode == __viewContext.user.companyCode });
                        if (checkdata.length == 0) {
                            let listWorkplace1 = [];
                            listWorkplace1.push({
                                companyId: __viewContext.user.companyId,
                                workpalceId: ''
                            });

                            service.getWorkPlace({ listWorkplace: listWorkplace1, workLocationCD: ko.unwrap(self.workLocationCD) }).done(function (data) {

                                self.setDataWorkPlace(data);

                                self.items = datagrid;
                                self.items.push(new GridItem(data.listCompany[0].companyCode, data.listCompany[0].companyName, '', ''));
                                let datas = _.orderBy(self.items, ['companyCode'], ['asc']);
                            });
                        } else {
                            self.items = datagrid;
                            let datas = _.orderBy(self.items, ['companyCode'], ['asc']);
                        }
                    });
                    $("#focusName").focus();
                }
            }
            else {
                self.isCreate(true);
                self.workLocationCD('');
                self.workLocationName('');
                self.radius(self.DEFAUT_VALUE_RADIUS);
                self.longitude(self.defautValueLocation);
                self.latitude(self.defautValueLocation);
                self.selectedWorkLocation(null);
                self.setDefaultSelectAreaCode();
                $("#focus").focus();
                errors.clearAll();

            }
        }
        //
        private findByIndex(index: number) {
            let self = this
            let data = _.nth(self.workPlacesList(), index);
            if (data !== undefined) {
                self.selectedWorkLocation(data.workLocationCD);
            }
            else {
                self.selectedWorkLocation(null);
            }
        }

        openB(): any {
            let self = this;
            let param = {
                workLocationCD: self.workLocationCD(),
                workLocationName: self.workLocationName()
            }
            nts.uk.ui.windows.setShared("CMM040B", param);
            nts.uk.ui.windows.sub.modal("/view/cmm/040/b/index.xhtml", { dialogClass: "no-close" }).onClosed(() => {
                self.listIpCancel(nts.uk.ui.windows.getShared('DataCMM040B'));

            });
        }

        buttonA5_3(): any {
            let self = this;
            let url = "https://www.google.co.jp/maps/place/" + self.valueA5_2();
            window.open(url);
        }

        newMode(): any {
            let self = this;
            errors.clearAll();
            self.isCreate(true);
            self.workLocationCD('');
            self.workLocationName('');
            self.valueA5_2('');
            self.radius(self.DEFAUT_VALUE_RADIUS);
            self.latitude(self.defautValueLocation);
            self.longitude(self.defautValueLocation);
            self.selectedWorkLocation(null);
            self.listSelectWorkplaceID = [];
            self.listWorkPlaceIDs = [];
            self.workplaceCode('');
            self.workplaceDisplayName('');
            // $("#grid2").remove();
            // $("#grid").append("<table id='grid2'></table>");
            self.items = [];
            let list1 = _.filter(self.loginInfo.listCompany, function (o) { return o.companyId == __viewContext.user.companyId; });
            //
            if (list1.length == 0) {
                let listWorkplace = [];
                listWorkplace.push({
                    companyId: __viewContext.user.companyId,
                    workpalceId: ''
                });
                service.getWorkPlace({ listWorkplace: listWorkplace, workLocationCD: ko.unwrap(self.workLocationCD) }).done(function (data) {

                    self.items.push(new GridItem(data.listCompany[0].companyCode, data.listCompany[0].companyName, '', ''));

                });


            }
            if (list1.length > 0) {
                self.items.push(new GridItem(list1[0].companyCode, list1[0].companyName, '', ''));
            }
            $("#focus").focus();
            self.listIpCancel([]);


            self.setDefaultSelectAreaCode();
        }

        add() {
            let self = this;
            $('#combo-box .ui-igcombo').ntsError('clear');
            if (( self.isNumberic(self.latitude()) || self.isNumberic(self.longitude())) && self.radius() == 9999) {
                $('#combo-box .ui-igcombo').ntsError('set', { messageId: "MsgB_1" ,messageParams:[nts.uk.resource.getText("CMM040_39")] });
                return;
            }
            $(".nts-input").trigger("validate");
            if (!$(".nts-input").ntsError("hasError")) {
                let listWorkplace = [];
                self.cdl008data();
                //  nts.uk.ui.windows.getShared('DataCMM040B');    
                let param = {
                    workLocationCD: self.workLocationCD(),
                    workLocationName: self.workLocationName(),
                    radius: self.radius() == 9999 ? null : self.radius(),
                    latitude: self.latitude(),
                    longitude: self.longitude(),
                    //update không cần set lại list IP vì màn B thêm trực tiếp rồi
                    listIPAddress: self.isCreate() ? self.listIpCancel() : [],
                    workplace: self.listWorkPlaceIDs[0],
                    regionCode: self.selectAreaCode()
                }
                // let select = 
                if (self.isCreate() === true) {

                    if (param.workplace) {
                        service.checkWorkplace({ workplaceID: param.workplace.workpalceId })
                            .done((data: any) => {
                                self.insert(param);
                            })
                            .fail((data: any) => {
                                nts.uk.ui.dialog
                                    .confirm({ messageId: data.messageId })
                                    .ifYes(() => {
                                        self.insert(param);
                                    })
                            })
                    } else {
                        self.insert(param);
                    }
                }
                else {
                    if (param.workplace === undefined || param.workplace.workpalceId === undefined) {
                        self.update(param);
                    }else {
                        service.checkWorkplace({ workplaceID: param.workplace.workpalceId })
                        .done((data: any) => {
                            self.update(param);
                        })
                        .fail((data: any) => {
                            if (self.changeWorkPlaceID === param.workplace.workpalceId) {
                                self.update(param);
                            }
                            else {
                                nts.uk.ui.dialog
                                    .confirm({ messageId: data.messageId })
                                    .ifYes(() => {
                                        self.update(param);
                                    })
                            }
                        })
                    }
                }
            }
        }

        insert(param: any) {
            const self = this;
            service.insert(param).done((result) => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.reloadData().done(() => {
                    self.selectedWorkLocation(self.workLocationCD());
                    self.isCreate(false);
                });
                $("#focusName").focus();
            }).fail((res: any) => {
                nts.uk.ui.dialog.alert({ messageId: res.messageId });
                $("#focusName").focus();
            }).always(() => {
                block.clear();
            });
        }

        update(param: any) {
            const self = this;

            service.update(param).done((result) => {
                nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                self.reloadData().done(() => {
                    self.selectedWorkLocation(self.workLocationCD());
                    self.selectedWorkLocation.valueHasMutated();
                });
                $("#focusName").focus();
            }).fail((res: any) => {
                nts.uk.ui.dialog.alert({ messageId: res.messageId }).then(() => {
                    let p = nts.uk.ui.errors.errorsViewModel();
                    p.option().show.subscribe(v => {
                        if (v == false) {
                            nts.uk.ui.errors.clearAll();
                        }
                    });
                    self.startPage();
                    if (self.workPlacesList().length > 0) {
                        self.findByIndex(0);
                    }
                });
            }).always(() => {
                block.clear();
            });
        }

        deleteWorkLocation(): any {
            const vm = new ko.ViewModel();
            let self = this;
            const role = __viewContext.user.role.systemAdmin;

            nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {

                if (role !== null) {
                    service.checkDelete().done((isSystem: boolean) => {
                        if (isSystem) {
                            nts.uk.ui.dialog.confirm({ messageId: "Msg_2215" })
                                .ifYes(() => {
                                    service.deleteWorkLocation(self.workLocationCD()).done((result) => {
                                        let index = _.findIndex(self.workPlacesList(), ['workLocationCD', self.workLocationCD()]);
                                        index = _.min([self.workPlacesList().length - 2, index]);
                                        vm.$blockui('invisible')
                                        .then(() => {
                                            self.reloadData().done(() => {
                                                if (index == -1) {
                                                    self.selectedWorkLocation(null);
                                                    self.workPlacesList([]);
                                                    errors.clearAll();
                                                    self.newMode();
                                                }
                                                else {
                                                    self.findByIndex(index);
                                                }
                                                nts.uk.ui.dialog.info({ messageId: "Msg_16" })
                                                .then(() => {
                                                    vm.$blockui('clear');
                                                });
                                            });
                                        })
                                    }).fail((res: any) => {
                                        nts.uk.ui.dialog.alert({ messageId: res.messageId }).then(function () {
                                            let index = _.findIndex(self.workPlacesList(), ['workLocationCD', self.workLocationCD()]);
                                            index = _.min([self.workPlacesList().length - 2, index]);
                                            self.reloadData().done(() => {
                                                self.findByIndex(0);
                                            });
                                        });
                                    }).always(() => {
                                        block.clear();
                                    });
                                })
                        } else {
                            service.deleteWorkLocation(self.workLocationCD()).done((result) => {
                                let index = _.findIndex(self.workPlacesList(), ['workLocationCD', self.workLocationCD()]);
                                index = _.min([self.workPlacesList().length - 2, index]);
                                vm.$blockui('invisible')
                                .then(() => {
                                    self.reloadData().done(() => {
                                        if (index == -1) {
                                            self.selectedWorkLocation(null);
                                            self.workPlacesList([]);
                                            errors.clearAll();
                                            self.newMode();
                                        }
                                        else {
                                            self.findByIndex(index);
                                        }
                                        nts.uk.ui.dialog.info({ messageId: "Msg_16" })
                                        .then(() => {
                                            vm.$blockui('clear');
                                        });
                                    });
                                })

                            }).fail((res: any) => {
                                nts.uk.ui.dialog.alert({ messageId: res.messageId }).then(function () {
                                });
                            }).always(() => {
                                block.clear();
                            });
                        }
                    })
                } else {
                    nts.uk.ui.dialog.info({ messageId: "Msg_2214" })
                }

            });
        }
        buttonA5_16(): any {

            let self = this;
            $.ajax({
                url: 'http://geoapi.heartrails.com/api/json',
                data: { method: 'suggest', matching: 'like', keyword: self.valueA5_2() }
            })
                .done(function (data) {
                    if (data.response.location == undefined) {
                        self.latitude('');
                        self.longitude('');
                        return;
                    }
                    let result = data.response.location[0];
                    self.latitude(result.y);
                    self.longitude(result.x);
                    errors.clearAll();
                });
        }
        private loadRadiusEnums(): JQueryPromise<Array<Enum>> {
            let self = this;

            let dfd = $.Deferred();
            service.radiusEnum().done(function (res: Array<Enum>) {
                self.radiusEnum(res);
                self.itemList(res)
                if(res.length){
                    self.itemList().push({value: 9999, fieldName: "M_9999", localizedName: ""});
                    }
                dfd.resolve();
            }).fail(function (res) {
                nts.uk.ui.dialog.alertError(res.message);
            });

            return dfd.promise();
        }
        public openDialogCDL008(): void {
            let self = this;
            let workplaceIds = [];
            let listWorkplace = [];
            nts.uk.ui.windows.setShared('inputCDL008', {
                baseDate: moment(new Date()).toDate(),
                selectedCodes: self.listSelectWorkplaceID,//職場ID 選択済項目
                isMultiple: false,//選択モード
                selectedSystemType: 1,//システム区分（0：共通、1：就業、2：給与、3：人事）
                isrestrictionOfReferenceRange: true,//参照範囲の絞込
                isShowNoSelectRow: false, //未選択表示
                showNoSelection: false,//Unselection Item
            }, true);
            nts.uk.ui.windows.sub.modal('/view/cdl/008/a/index.xhtml').onClosed(function (): any {
                // Check is cancel.
                if (nts.uk.ui.windows.getShared('CDL008Cancel')) {
                    return;
                }
                //view all code of selected item 
                let output = nts.uk.ui.windows.getShared('workplaceInfor');

                // output = _.sortBy(output, 'code');

                self.workplaceCode(output[0].code);
                self.workplaceDisplayName(output[0].name);

                self.listSelectWorkplaceID = [];
                self.listWorkPlaceIDs = [];
                for (let i = 0; i < output.length; i++) {
                    self.listWorkPlaceIDs.push({ companyId: self.LoginCompanyId, workpalceId: output[i].id });
                    self.listSelectWorkplaceID.push(output[i].id);
                }
                // self.cdl008data(output);
                let code = '';
                let name = '';
                for (i = 0; i < output.length; i++) {
                    code += output[i].code + '</br>';
                    name += output[i].name + '</br>';
                }
                //
                let row = $(document.activeElement).parents()[3].firstChild.innerText;
                // $("#grid2").ntsGrid("updateRow", row, { workplaceCode: code, workplaceName: name });

            })
        }


    }
    export class WorkLocation {
        // 職場コード
        workLocationCD: string;
        // 職場名称
        workLocationName: string;
        constructor(workLocationCD: string, workLocationName: string) {
            this.workLocationCD = workLocationCD;
            this.workLocationName = workLocationName
        }
    }
    export class BonusPaySetting {
        // 会社ID
        companyId: KnockoutObservable<string>;
        // 会社名
        name: KnockoutObservable<string>;
        // 会社コード
        code: KnockoutObservable<string>;
        constructor(companyId: string, name: string, code: string) {
            this.companyId = ko.observable(companyId);
            this.name = ko.observable(name);
            this.code = ko.observable(code);
        }
    }
    class ItemModel {
        value: string;
        fieldName: string;

        constructor(value: string, fieldName: string) {
            this.value = value;
            this.fieldName = fieldName;
        }
    }
    class GridItem {
        // 会社コード
        companyCode: string;
        // 会社名
        companyName: string;
        //  職場コード
        workplaceCode: string;
        // 職場名称
        workplaceName: string;
        constructor(companyCode: string, companyName: string, workplaceCode: string, workplaceName: string) {
            this.companyCode = companyCode;
            this.companyName = companyName;
            this.workplaceCode = workplaceCode;
            this.workplaceName = workplaceName;
        }
    }


}
