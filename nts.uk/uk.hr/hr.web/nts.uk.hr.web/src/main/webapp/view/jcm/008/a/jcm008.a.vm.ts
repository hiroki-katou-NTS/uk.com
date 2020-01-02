module jcm008.a {
    import ajax = nts.uk.request.ajax;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import liveView = nts.uk.request.liveView;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import close = nts.uk.ui.windows.close;
    import dialog = nts.uk.ui.dialog;
    var block = nts.uk.ui.block;

    export class ViewModel {
        searchFilter: SearchFilterModel;
        retirementSettings: KnockoutObservable<Array<string>>;
        
        selectedRetirementEmployee: KnockoutObservable<any>;
        plannedRetirements: KnockoutObservable<Array<PlannedRetirementDto>>;
        employeeInfo: KnockoutObservable<IEmployee>;
       
        constructor() {
            let self = this;
            self.searchFilter = new SearchFilterModel();
            self.plannedRetirements = ko.observableArray([]);
            self.bindRetirementDateSettingGrid();
            self.searchFilter.retirementCourses = ko.observableArray([]);
            self.employeeInfo = ko.observable({});
            $(".employee-info-pop-up").ntsPopup("hide");
        }

        /** start page */
        start() {
            let self = this;
            block.grayout();
            let dfd = $.Deferred<any>();
            service.getData().done((data : IStartPageDto) => {
                console.log(data);
                let dateDisplaySet = data.dateDisplaySettingPeriod;
                self.searchFilter.retirementPeriod({startDate: dateDisplaySet.periodStartdate, endDate: dateDisplaySet.periodEnddate});
                _.map(data.retirementCourses, (c) => {
                    c.retirementAge = c.retirementAge + '分';
                    return c;
                });
                self.searchFilter.retirementCourses(data.retirementCourses);

                let retirementAge = _.map(self.searchFilter.retirementCourses(), (rc) => {
                    return new RetirementAgeSetting(rc.retirementAge.replace('分', '') ,rc.retirementAge);
                });

                self.searchFilter.retirementAges(_.uniqBy(retirementAge, 'code'));
                self.bindRetirementAgeGrid();
                dfd.resolve();
                // block.clear();
            }).fail((error) => {
                console.log('Get Data Fail');
                dfd.reject();
                dialog.info(error);
            }).always(() => {
                block.clear();
            });
            
            return dfd.promise();
        }

        /** event when click register */
        getRetirementData() {
            let self = this;
            let param = new ISearchParams(self.searchFilter);
            
            block.grayout();
            let dfd = $.Deferred<any>();
            service.searchRetireData(param)
                .done((res :ISearchResult) => {
                    console.log(res);
                    let merged = _.merge(_.keyBy(res.retiredEmployees, 'sid'), _.keyBy(res.employeeImports, 'employeeId'));
                    let values = _.values(merged);
                    console.log(values);
                    self.plannedRetirements(values);
                    self.bindRetirementDateSettingGrid();
                    self.searchFilter.confirmCheckRetirementPeriod(false);
                })
                .fail((error) => {
                    dfd.reject();
                    console.log(error);
                    if(error.messageId == "MsgJ_JCM008_5") {
                        confirm({ messageId: "MsgJ_JCM008_5" }).ifYes(() => {
                            self.searchFilter.confirmCheckRetirementPeriod(true);
                            self.getRetirementData();
                        });
                    } else {
                        dialog.info(error);
                    }
                })
                .always(() => {
                    block.clear();
                });
        }

        public register() {
            let self = this;
            
            let settingChanges = _.filter(self.plannedRetirements(), (p) => { 
                return _.find($("#retirementDateSetting").ntsGrid("updatedCells"), {'rowId': p.key}) 
            });

            let groupChanges = _.groupBy($("#retirementDateSetting").ntsGrid("updatedCells"), 'rowId');
            
            let retiInfos = _.map(settingChanges, (retire) => { 
                let changed = _.find(groupChanges, (value, key) => { 
                    return key == retire.key;
                });
                for (let row in changed) {
                    retire[changed[row].columnKey] = changed[row].value;
                }

                return retire;
            });
            console.log(retiInfos);
            let data = {
                retiInfos: retiInfos
            };
            block.grayout();
            service.register(data)
                .done(() => {
                    dialog.info({ messageId: "Msg_15" });
                })
                .fail((error) => {
                    dialog.info(error);
                })
                .always(() => {
                    block.clear();
                });
        }

        public bindRetirementAgeGrid(): void {
            let self = this;
            $('#retirementAgeInfo').ntsGrid({
                autoGenerateColumns: false,
                columns: [
                    { headerText: getText('JCM008_A222_13'), key: 'employmentName', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_14'), key: 'retirementAge', dataType: 'string', width: '70px' },
                    { headerText: getText('JCM008_A222_15'), key: 'retireDateBase', dataType: 'string', width: '280px' },

                ],
                dataSource: self.searchFilter.retirementCourses(),
                dataSourceType: 'json',
                responseDataKey: 'results'
            });
        }

        public bindRetirementDateSettingGrid(): void {
            let self = this;
            let comboColumns = [{ prop: 'retirePlanCourseName', length: 3 }];
            if ($('#retirementDateSetting').data("igGrid")) {
                $('#retirementDateSetting').ntsGrid("destroy");
            };
            let dataSources = self.plannedRetirements();

            dataSources = _.map(dataSources, (data) => {
                data.key = data.sid.replace(/[^\w\s]/gi,'');
                data.ageDisplay = data.age + '分';
                return data;
            });

            $('#retirementDateSetting').ntsGrid({
                autoGenerateColumns: false,
                width: '1200px',
                height: '500px',
                primaryKey: 'key',
                virtualization: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
                // autoFitWindow: true,
                columns: [
                    { headerText: 'key', key: 'key', dataType: 'string' },
                    { headerText: getText('JCM008_A222_22'), key: 'flag', dataType: 'boolean', width: '70px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                    { headerText: getText('JCM008_A222_23'), key: 'extendEmploymentFlg', dataType: 'number', width: '80px', ntsControl: 'RetirementStatusCb' },
                    { headerText: getText('JCM008_A222_24'), key: 'registrationStatus', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_25'), key: 'desiredWorkingCourseId', dataType: 'number', width: '100px', ntsControl: 'WorkingCourseCb' },
                    // { headerText: getText('JCM008_A222_25'), key: 'desiredWorkingCourseName', dataType: 'string', width: '100px' },
                    { headerText: getText('JCM008_A222_26'), key: 'employeeCode', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_27'), key: 'employeeName', dataType: 'string', width: '100px', ntsControl: 'EmployeeName' },
                    { headerText: getText('JCM008_A222_28'), key: 'businessnameKana', dataType: 'string', width: '100px' },
                    { headerText: getText('JCM008_A222_29'), key: 'birthday', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_30'), key: 'ageDisplay', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_31'), key: 'departmentName', dataType: 'string', width: '90px' },
                    { headerText: getText('JCM008_A222_32'), key: 'employmentName', dataType: 'string', width: '90px' },
                    { headerText: getText('JCM008_A222_33'), key: 'dateJoinComp', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_34'), key: 'retirementDate', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_35'), key: 'releaseDate', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_35_1'), key: 'inputDate', dataType: 'string', width: '95px' },
                    {
                        headerText: getText('JCM008_A222_36'),
                        group: [
                            { headerText: getText('JCM008_A222_54_1'), key: 'hrEvaluation1', dataType: 'string', width: '40px' },
                            { headerText: getText('JCM008_A222_54_2'), key: 'hrEvaluation2', dataType: 'string', width: '40px' },
                            { headerText: getText('JCM008_A222_54_3'), key: 'hrEvaluation3', dataType: 'string', width: '40px' }
                        ]
                    },
                    {
                        headerText: getText('JCM008_A222_37'),
                        group: [
                            { headerText: getText('JCM008_A222_55_1'), key: 'healthStatus1', dataType: 'string', width: '40px' },
                            { headerText: getText('JCM008_A222_55_2'), key: 'healthStatus2', dataType: 'string', width: '40px' },
                            { headerText: getText('JCM008_A222_55_3'), key: 'healthStatus3', dataType: 'string', width: '40px' }
                        ]
                    }, 
                    {
                        headerText: getText('JCM008_A222_38'),
                        group: [
                            { headerText: getText('JCM008_A222_56_1'), key: 'stressStatus1', dataType: 'string', width: '40px' },
                            { headerText: getText('JCM008_A222_56_2'), key: 'stressStatus2', dataType: 'string', width: '40px' },
                            { headerText: getText('JCM008_A222_56_3'), key: 'stressStatus3', dataType: 'string', width: '40px' }
                        ]
                    },
                    { headerText: getText('JCM008_A222_39'), key: 'interviewRecord', dataType: 'string', width: '100px', ntsControl: 'InterviewRecord' },
                ],
                dataSource: dataSources,
                tabIndex: 11,
                features: [
                   {
                       name: "Selection", mode: "row",multipleSelection: true,
                       rowSelectionChanged: function (evt, ui) {
                           let itemSelected = _.find(self.selectedRetirementEmployee, function (o) { return o.sid == ui.row.sid; });
                           if (itemSelected) {
                               self.selectedRetirementEmployee(itemSelected);
                           }
                       }
                   },
                    {
                        name: 'Filtering',type: 'local',mode: 'simple',
                        dataFiltered: function (evt, ui) {
                            // $('#retirementDateSetting').css('height', '425px');
                        }
                    },
                    {name: 'Paging', pageSize: 10,currentPageIndex: 0},
                    {name: 'Resizing'},
                    {name: 'MultiColumnHeaders'}
                ],
                ntsFeatures: [
                      { name: 'CopyPaste' },
                      { name: 'CellEdit' }
                ],
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'RetirementStatusCb', width: '75px', options: [new RetirementStatus(0, '退職'), new RetirementStatus(1, '継続')], optionsValue: 'code', optionsText: 'name', columns: [{ prop: 'name', length: 2 }], controlType: 'ComboBox', enable: true },
                    { name: 'WorkingCourseCb', width: '80px', options: self.searchFilter.retirementCourses, optionsValue: 'retirePlanCourseId', optionsText: 'retirePlanCourseName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                    { name: 'EmployeeName', click: function (id, key, el) { self.showModal(id, key, el); }, controlType: 'LinkLabel' },
                    { name: 'InterviewRecord', click: function (id, key, el) { console.log(el); }, controlType: 'LinkLabel' },
                    
                ]
            });
        }

        public showModal(id, key, el) {
            // $('.nts-grid-control-' + key + '-' + id).append("<b>Appended text</b>");
            let self = this;
            console.log(key);
            console.log(id);
            let emp = {
                code: id,
                name: '社員名 員名',
                kanaName: '社員名 ',
                sex:'性別',
                dob:'1991/04/11',
                age:'29分',
                department:'部門 学科 局 部科売り場',
                position: '職位職位',
                employment:'雇用雇用',
                image: 'https://scontent.fhan2-3.fna.fbcdn.net/v/t1.0-9/s960x960/67660458_2163133103815091_6832643697729863680_o.jpg?_nc_cat=108&_nc_ohc=PzzrUkG6zBAAQkMqXJeoGd7dj9YkJUgyqnSPGDqzbcJ2uPPb_DzzpiSRw&_nc_ht=scontent.fhan2-3.fna&oh=51b9cc5985e9e57dcae281e966c62f20&oe=5E8674C7'
            };

            self.employeeInfo(emp);
            $(".employee-info-pop-up").ntsPopup({
                trigger: '.nts-grid-control-' + key + '-' + id,
                position: {
                    my: "left top",
                    at: "left bottom",
                    of: '.nts-grid-control-' + key + '-' + id
                },
                showOnStart: true,
                dismissible: true
            });
            // Toggle
            
        };

        public choseDepartment(){
            let self = this;
            block.grayout();
            setShared('inputCDL008', {  
                selectedCodes: self.searchFilter.department().map(function(elem){
                    return elem.workplaceId;
                }),
                baseDate: moment(new Date()).toDate(),
                isMultiple: true, 
                selectedSystemType:1 , 
                isrestrictionOfReferenceRange:false , 
                showNoSelection:false , 
                isShowBaseDate:true });
            modal('hr', '/view/jdl/0110/a/index.xhtml').onClosed(function(){
                block.clear();
                let data = getShared('outputCDL008');
                self.searchFilter.department(data);
            });
        }

        public choseEmployment(){
            let self = this;
            block.grayout();
            setShared('CDL002Params', {  
                selectedCodes: self.searchFilter.employment().map(function(elem){
                    return elem.workplaceId;
                }),
                baseDate: moment(new Date()).toDate(),
                isMultiple: true, 
                selectedSystemType:1 , 
                isrestrictionOfReferenceRange:false , 
                showNoSelection:false , 
                isShowBaseDate:true });
            modal('hr', '/view/jdl/0080/a/index.xhtml').onClosed(function(){
                block.clear();
                let data = getShared('CDL002Output');
                self.searchFilter.employment(data);
            });
        }

        /**
            * Export excel
            */
           public exportExcel(): void {
            let self = this;
            self.searchFilter.confirmCheckRetirementPeriod(true);
            let param = new ISearchParams(self.searchFilter);
            block.grayout();
            service.outPutFileExcel(param).done((data1) => {
                block.clear();
            })


        }
    }

}

