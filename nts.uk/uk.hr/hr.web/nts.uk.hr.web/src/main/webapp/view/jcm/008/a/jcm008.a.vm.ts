module jcm008.a {
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import dialog = nts.uk.ui.dialog;
    import liveView = nts.uk.request.liveView;
    var block = nts.uk.ui.block;

    export class ViewModel {
        searchFilter: SearchFilterModel;
        retirementSettings: KnockoutObservable<Array<string>>;
        selectedRetirementEmployee: KnockoutObservable<any>;
        plannedRetirements: KnockoutObservable<Array<PlannedRetirementDto>>;
        employeeInfo: KnockoutObservable<IEmployee>;
        referEvaluationItems: KnockoutObservable<Array<ReferEvaluationItem>>;
        hidedColumns: Array<String>;
        constructor() {
            let self = this;
            self.searchFilter = new SearchFilterModel();
            self.plannedRetirements = ko.observableArray([]);
            self.searchFilter.retirementCourses = ko.observableArray([]);
            self.searchFilter.retirementCoursesEarly = ko.observableArray([]);
            self.searchFilter.retirementCoursesStandard = ko.observableArray([]);
            self.employeeInfo = ko.observable({});
            self.referEvaluationItems = ko.observableArray([]);
            self.hidedColumns = [];
            $(".employee-info-pop-up").ntsPopup("hide");

            $(window).resize(function() {
                self.setScroll();
            });

            self.searchFilter.retirementCourses.subscribe((newVal) => {
                
                self.searchFilter.retirementCoursesEarly(
                    _.chain(newVal)
                        .filter((o) => { return o.retirePlanCourseClass != 0; })
                        .uniqBy('retirePlanCourseCode')
                        .sortBy(['employmentCode', 'retirementAge'])
                        .value()
                );
                
                self.searchFilter.retirementCoursesStandard(
                    _.chain(newVal)
                        .filter((o) => { return o.retirePlanCourseClass == 0; })
                        .sortBy(['employmentCode', 'retirementAge'])
                        .value()
                    );
            });
        }
        

        /** start page */
        start() {
            let self = this;
            block.grayout();
            let dfd = $.Deferred<any>();
            service.getData().done((data: IStartPageDto) => {
                console.log(data);
                self.referEvaluationItems(data.referEvaluationItems);
                let dateDisplaySet = data.dateDisplaySettingPeriod;
                self.searchFilter.retirementPeriod({ startDate: dateDisplaySet.periodStartdate, endDate: dateDisplaySet.periodEnddate });
               
                _.map(data.retirementCourses, (c) => {
                    c.retirementAge = c.retirementAge + '歳';
                    return c;
                });
                
                
                self.searchFilter.retirementCourses(_.sortBy(data.retirementCourses, ['employmentCode', 'retirementAge']));

                let retirementAge = _.map(self.searchFilter.retirementCoursesEarly(), (rc) => {
                    return new RetirementAgeSetting(rc.retirementAge.replace('歳', ''), rc.retirementAge);
                });
                
                self.searchFilter.retirementAges(_.sortBy(_.uniqBy(retirementAge, 'code'), 'code'));
                self.bindRetirementAgeGrid();
                self.bindRetirementDateSettingGrid();
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
                .done((res: ISearchResult) => {
                    let mergedEmpInfo = _.merge(_.keyBy(res.retiredEmployees, 'sid'), _.keyBy(res.employeeImports, 'employeeId'));
                    if(res.interView && res.interView.listInterviewRecordAvailability) {
                        mergedEmpInfo = _.merge(_.keyBy(mergedEmpInfo, 'sid'), _.keyBy(res.interView.listInterviewRecordAvailability, 'employeeID'));
                    }
                    self.plannedRetirements(_.values(mergedEmpInfo));
                    self.bindRetirementDateSettingGrid();
                    self.searchFilter.confirmCheckRetirementPeriod(false);
                    self.setScroll();
                })
                .fail((error) => {
                    dfd.reject();
                    console.log(error);
                    if (error.messageId == "MsgJ_JCM008_5") {
                        confirm({ messageId: "MsgJ_JCM008_5" }).ifYes(() => {
                            self.searchFilter.confirmCheckRetirementPeriod(true);
                            self.getRetirementData();
                        });
                    } else {
                        dialog.bundledErrors(error);
                    }
                    self.plannedRetirements([]);
                    self.bindRetirementDateSettingGrid();
                    self.setScroll();
                })
                .always(() => {
                    block.clear();
                });
        }

        public register() {
            let self = this;
            block.grayout();

            let groupChanges = _.groupBy($("#retirementDateSetting").ntsGrid("updatedCells"), 'rowId');
            let datas = _.filter(self.plannedRetirements(), (r) => {return !(r.status === 2 || r.status === 3) })
            let retiInfos = _.map(datas, (retire) => {
                let changed = _.find(groupChanges, (value, key) => {
                    return key == retire.rKey;
                });
                // アルゴリズム[定年退職者情報の新規登録_変更]を実行する(thực hiện thuật toán [tạo mới/thay đổi thông tin người nghỉ hưu])
                if (changed) {
                    for (let row in changed) {
                        if(changed[row].columnKey === 'registrationStatus') {
                            retire.extendEmploymentFlg = changed[row].value
                        }

                        if(changed[row].columnKey === 'flag') {
                            if(changed[row].value) {
                                retire.pendingFlag = 1;
                                retire.status = 0;
                            } else {
                                retire.pendingFlag = 0;
                                retire.status = 1;
                            }    
                        }
                         
                        retire[changed[row].columnKey] = changed[row].value;
                    }
                } else {
                    if (retire.status != 3 && retire.status != 2) {
                        if (retire.flag) {
                            retire.pendingFlag = 1;
                            retire.status = 0;
                        } else {
                            retire.pendingFlag = 0;
                            retire.status = 1;
                        }
                    }
                }
                
                let selectedPlan = _.find(self.searchFilter.retirementCourses(), (plan) => {
                    return plan.retirePlanCourseId === retire.desiredWorkingCourseId;
                });
                
                if (selectedPlan) {
                    retire.desiredWorkingCourseCd = selectedPlan.retirePlanCourseCode;
                    retire.desiredWorkingCourseName = selectedPlan.retirePlanCourseName;
                }
        
                return retire;
            });

            let data = {
                retiInfos: retiInfos
            };
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
                height: '160px',
                columns: [
                    { headerText: getText('JCM008_A222_13'), key: 'employmentName', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_14'), key: 'retirementAge', dataType: 'string', width: '70px' },
                    { headerText: getText('JCM008_A222_15'), key: 'retireDateBase', dataType: 'string', width: '262px' },

                ],
                dataSource: self.searchFilter.retirementCoursesStandard(),
                dataSourceType: 'json',
                responseDataKey: 'results'
            });
        }

        public bindRetirementDateSettingGrid(): void {
            let self = this;
            if ($('#retirementDateSetting').data("igGrid")) {
                $('#retirementDateSetting').ntsGrid("destroy");
            };
            let comboColumns = [{ prop: 'retirePlanCourseName', length: 10 }];
            let dataSources = self.plannedRetirements();
            let rowStates = [];
            let cellStates = [];
            let interviewRecordTxt = getText('JCM008_A222_57');
            dataSources = _.map(dataSources, (data) => {
                data.rKey = data.sid.replace(/[^\w\s]/gi, '');
                // data.rKey = idx ++;
                data.ageDisplay = data.retirementAge + '歳';
                if (data.presence) {
                    data.interviewRecordTxt = interviewRecordTxt;
                }

                data.flag = data.pendingFlag === 1 ? true : false;
                
                switch (data.status) {
                    case 0:
                        data.registrationStatus = '';
                        if(data.notificationCategory === 1) {
                            cellStates.push(new CellState(data.rKey, 'desiredWorkingCourseId', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        }
                        break;
                    case 1:
                        data.registrationStatus = '承認待ち';
                        if(data.notificationCategory === 1) {
                            cellStates.push(new CellState(data.rKey, 'desiredWorkingCourseId', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        }
                        break;
                    case 2:
                        data.registrationStatus = '反映待ち';
                        cellStates.push(new CellState(data.rKey, 'flag', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        cellStates.push(new CellState(data.rKey, 'extendEmploymentFlg', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        cellStates.push(new CellState(data.rKey, 'desiredWorkingCourseId', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        break;
                    case 3:
                        data.registrationStatus = '反映済み';
                        cellStates.push(new CellState(data.rKey, 'flag', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        cellStates.push(new CellState(data.rKey, 'extendEmploymentFlg', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        cellStates.push(new CellState(data.rKey, 'desiredWorkingCourseId', [nts.uk.ui.jqueryExtentions.ntsGrid.color.Disable]));
                        break;
                    default:
                        break;
                }
                return data;
            });
            
            let fixedClmSetting = [
                { columnKey: 'rKey', isFixed: true },
                { columnKey: 'flag', isFixed: true },
                { columnKey: 'extendEmploymentFlg', isFixed: true },
                { columnKey: 'registrationStatus', isFixed: true }
            ];
            if(self.searchFilter.retirementCoursesEarly() && self.searchFilter.retirementCoursesEarly().length > 0) {
                fixedClmSetting.push({ columnKey: 'desiredWorkingCourseId', isFixed: true });
            }
            fixedClmSetting = fixedClmSetting.concat([
                { columnKey: 'employeeCode', isFixed: true },
                { columnKey: 'employeeName', isFixed: true }
            ]);
            let clConfig = self.buildRetirementDateSettingCol();
            let columns = clConfig.columns;
            let hidedColumnsCf = clConfig.hidedColumnsCf;
            let sheets = [];
            _.forEach(columns, (col) => {
                if( _.indexOf(_.map(fixedClmSetting, 'columnKey'), col.key) === -1 ) {
                    if(col.group && col.group.length > 0) {
                        _.forEach(col.group, (gr) => {
                            sheets.push(gr.key);    
                        });
                    } else {
                        sheets.push(col.key);
                    }
                }
            });

            let retirementCourses = [{
                employmentCode: "",
                employmentName: "",
                retirePlanCourseClass: 0,
                retirementAge: null,
                retireDateBase: "",
                retireDateTerm: { retireDateTerm: 0, retireDateSettingDate: 0 },
                retirePlanCourseId: null,
                retirePlanCourseCode: "",
                retirePlanCourseName: "",
                durationFlg: 0
            }];
            retirementCourses = retirementCourses.concat(_.sortBy(self.searchFilter.retirementCoursesEarly(), 'retirePlanCourseCode'));

            $('#retirementDateSetting').ntsGrid({
                autoGenerateColumns: false,
                width: '1200px',
                height: '500px',
                primaryKey: 'rKey',
                virtualization: true,
                rowVirtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: true,
                // autoFitWindow: true,
                columns: columns,
                dataSource: dataSources,
                tabIndex: 11,
                features: [
                    {
                        name: "Selection", mode: "row", multipleSelection: true,
                        rowSelectionChanged: function (evt, ui) {
                            let itemSelected = _.find(self.selectedRetirementEmployee, function (o) { return o.sid == ui.row.sid; });
                            if (itemSelected) {
                                self.selectedRetirementEmployee(itemSelected);
                            }
                        }
                    },
                    {
                        name: 'Filtering', type: 'local', mode: 'simple',
                        dataFiltered: function (evt, ui) {

                        }
                    },
                    { name: 'Paging', pageSize: 10, currentPageIndex: 0 },
                    { name: 'Resizing' },
                    { name: 'Hiding' , columnSettings: hidedColumnsCf},
                    { name: 'MultiColumnHeaders' },
                    { name: 'ColumnFixing', fixingDirection: 'left', showFixButtons: false, columnSettings: fixedClmSetting},
                    
                ],
                ntsFeatures: [
                    { name: 'CopyPaste' },
                    { name: 'CellEdit' },
                    {
                        name: 'CellState',
                        rowId: 'rowId',
                        columnKey: 'columnKey',
                        state: 'state',
                        states: cellStates
                    },
                    {
                        name: 'RowState',
                        rows: rowStates
                    },
                    { name: "Sheet",
                            initialDisplay: "sheet1",
                            sheets: [
                                { name: "sheet1", text: "Sheet 1", columns: sheets }
                            ]
                    },
                ],
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'RetirementStatusCb', width: '75px', options: [new RetirementStatus(0, '退職'), new RetirementStatus(1, '継続')], optionsValue: 'code', optionsText: 'name', columns: [{ prop: 'name', length: 2 }], controlType: 'ComboBox', enable: true },
                    { name: 'WorkingCourseCb', width: '150px', options: retirementCourses, optionsValue: 'retirePlanCourseId', optionsText: 'retirePlanCourseName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                    { name: 'EmployeeName', click: function (id, key, el) { self.showModal(id, key, el); }, controlType: 'LinkLabel' },
                    { name: 'InterviewRecord', click: function (id, key, el) { console.log(el); }, controlType: 'LinkLabel' },

                ]
            });
            self.bindHidingEvent();
        }

        private buildRetirementDateSettingCol() {
            let self = this;
            let columns = [
                { headerText: 'key', key: 'rKey', dataType: 'string' },
                { headerText: getText('JCM008_A222_22'), key: 'flag', dataType: 'boolean', width: '103px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                { headerText: getText('JCM008_A222_23'), key: 'extendEmploymentFlg', dataType: 'number', width: '110px', ntsControl: 'RetirementStatusCb' },
                { headerText: getText('JCM008_A222_24'), key: 'registrationStatus', dataType: 'string', width: '110px', ntsControl: 'Label' }
            ];

            let hidedColumnsCf = [];
            if (self.searchFilter.retirementCoursesEarly() && self.searchFilter.retirementCoursesEarly().length > 0) {
                columns.push({ headerText: getText('JCM008_A222_25'), key: 'desiredWorkingCourseId', dataType: 'number', width: '160px', ntsControl: 'WorkingCourseCb' });
            }
           
            columns = columns.concat([
                { headerText: getText('JCM008_A222_26'), key: 'employeeCode', dataType: 'string', width: '110px', ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_27'), key: 'employeeName', dataType: 'string', width: '100px', ntsControl: 'EmployeeName' },
                { headerText: getText('JCM008_A222_28'), key: 'businessnameKana', dataType: 'string', width: '120px', ntsControl: 'Label' },
                { headerText: getText('JCM008_A222_29'), key: 'birthday', dataType: 'string', width: '95px', ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_30'), key: 'ageDisplay', dataType: 'string', width: '80px' , ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_31'), key: 'departmentName', dataType: 'string', width: '90px' , ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_32'), key: 'employmentName', dataType: 'string', width: '90px' , ntsControl: 'Label' },
                { headerText: getText('JCM008_A222_33'), key: 'dateJoinComp', dataType: 'string', width: '95px' , ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_34'), key: 'retirementDate', dataType: 'string', width: '95px' , ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_35'), key: 'releaseDate', dataType: 'string', width: '95px' , ntsControl: 'Label'},
                { headerText: getText('JCM008_A222_35_1'), key: 'inputDate', dataType: 'string', width: '95px', ntsControl: 'Label'}
            ]);

            if (self.referEvaluationItems().length > 0) {
                self.referEvaluationItems().forEach((item) => {
                    if(item.evaluationItem == EvaluationItem.PERSONNEL_ASSESSMENT && item.displayNum > 0 && item.usageFlg) {
                        let hcGroups = [];
                        for (let hc = 0; hc < item.displayNum; hc ++) {
                            let hcKey = 'hrEvaluation' + (hc + 1);
                            hcGroups.push({ headerText: getText('JCM008_A222_36_' + (hc + 3)), key: hcKey, dataType: 'string', width: '40px' , ntsControl: 'Label'});
                            hidedColumnsCf.push({columnKey: hcKey, allowHiding: false});
                        }

                        columns.push(hcGroups.length > 1 ? {headerText: getText('JCM008_A222_36'), width: '80px', group: hcGroups} : { headerText: getText('JCM008_A222_36'), key: 'hrEvaluation1', dataType: 'string', width: '80px', ntsControl: 'Label' });
                    }
                    
                    if(item.evaluationItem == EvaluationItem.HEALTH_CONDITION && item.displayNum > 0 && item.usageFlg) {
                        let paGroups = [];
                        for (let pa = 0; pa < item.displayNum; pa ++) {
                            let paKey = 'healthStatus' + (pa + 1)
                            paGroups.push({ headerText: getText('JCM008_A222_37_' + (pa + 3)), key: paKey, dataType: 'string', width: '40px' , ntsControl: 'Label'});
                            hidedColumnsCf.push({columnKey: paKey, allowHiding: false});
                        }
                        columns.push(paGroups.length > 1 ? {headerText: getText('JCM008_A222_37'), width: '80px', group: paGroups} : { headerText: getText('JCM008_A222_37'), key: 'healthStatus1', dataType: 'string', width: '80px', ntsControl: 'Label' });
                    }
                    
                    if(item.evaluationItem == EvaluationItem.STRESS_CHECK && item.displayNum > 0 && item.usageFlg) {
                        let scGroups = [];
                        for (let sc = 0; sc < item.displayNum; sc ++) {
                            let scKey = 'stressStatus' + (sc + 1);
                            scGroups.push({ headerText: getText('JCM008_A222_38_' + (sc + 3)), key: scKey, dataType: 'string', width: '40px' , ntsControl: 'Label'});
                            hidedColumnsCf.push({columnKey: scKey, allowHiding: false});
                        }
                        columns.push( scGroups.length > 1 ? {headerText: getText('JCM008_A222_38'), width: '80px', group: scGroups} : { headerText: getText('JCM008_A222_38'), key: 'stressStatus1', dataType: 'string', width: '80px', ntsControl: 'Label'});
                    }
                });
            }

            columns.push({ headerText: getText('JCM008_A222_39'), key: 'interviewRecordTxt', dataType: 'string', width: '100px', ntsControl: 'InterviewRecord' });
            _.forEach(self.hidedColumns, (key) => {
                hidedColumnsCf.push({columnKey: key, allowHiding: true, hidden: true});
            });

            return {columns: columns, hidedColumnsCf: hidedColumnsCf};
        }

        public showModal(id, key, el) {
            // $('.nts-grid-control-' + key + '-' + id).append("<b>Appended text</b>");
            let self = this;
            let selectedEmp = _.findLast($("#retirementDateSetting").igGrid("option", "dataSource"), (retiredEmp) => {
                return retiredEmp.rKey === id;
            });


            let param = {
                employeeId: selectedEmp.employeeId,
                personId: selectedEmp.personID,
                baseDate: moment(new Date()).format("YYYY/MM/DD"),
                getDepartment: true,
                getPosition: true,
                getEmployment: true
            };
            block.grayout();
            service.findEmployeeInfo(param).done((data) => {
                block.clear();
                
                if (!data) {
                    return;
                }
                
                let emp = {
                    code: data.employeeCode,
                    name: data.businessName,
                    kanaName: data.businessNameKana,
                    sex: data.gender === 1 ? '男性' : '女性',
                    dob: data.birthday,
                    age: data.age + '歳',
                    department: data.department?data.department.departmentName:null,
                    position: data.position?data.position.positionName:null,
                    employment: data.employment?data.employment.employmentName:null,
                    image: data.avatarFile ? liveView(data.avatarFile.facePhotoFileID) : 'images/avatar.png',
                    showDepartment: param.getDepartment,
                    showPosition: param.getPosition,
                    showEmployment: param.getEmployment,
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
            })
        };

        public choseDepartment() {
            let self = this;
            block.grayout();
            setShared('inputCDL008', {
                selectedCodes: self.searchFilter.department().map(function (elem) {
                    return elem.id;
                }),
                baseDate: moment(new Date()).toDate(),
                isMultiple: true,
                selectedSystemType: 1,
                isrestrictionOfReferenceRange: false,
                showNoSelection: false,
                isShowBaseDate: true,
                startMode: 1
            });
            modal('hr', '/view/jdl/0110/a/index.xhtml').onClosed(function() {
                block.clear();
                let data = getShared('outputDepartmentJDL0110') ? getShared('outputDepartmentJDL0110') : [],
                    isCancel = getShared('CDL008Cancel') ? getShared('CDL008Cancel') : false
                    ;
                if (!isCancel) {
                    self.searchFilter.department(data);
                }
            });
        }

        public choseEmployment() {
            let self = this;
            block.grayout();
            setShared('CDL002Params', {
                selectedCodes: self.searchFilter.employment().map(function (elem) {
                    return elem.code;
                }),
                baseDate: moment(new Date()).toDate(),
                isMultiple: true,
                selectedSystemType: 1,
                isrestrictionOfReferenceRange: false,
                showNoSelection: false,
                isShowBaseDate: true,
                isShowWorkClosure: false
            });
            modal('hr', '/view/jdl/0080/a/index.xhtml').onClosed(function () {
                block.clear();
                let data = getShared('CDL002Output') ? getShared('CDL002Output') : [],
                    isCancel = getShared('CDL002Cancel') ? getShared('CDL002Cancel') : false;
                
                if (!isCancel) {
                    self.searchFilter.employment(data);
                }
            });
        }

        public caculator(): any {
                let contentArea = $("#contents-area")[0].getBoundingClientRect().height
                //height of combobox of display page size = 44
                let groupArea = 45 + $("#main-left-area")[0].getBoundingClientRect().height;
            
                let contentAreaWidth = $("#contents-area")[0].getBoundingClientRect().width;

                let gridAreaWidth = $("#main-area")[0].getBoundingClientRect().width;
                return { contentAreaHeight: contentArea, gridAreaHeight: contentArea - groupArea, contentAreaWidth: contentAreaWidth, gridAreaWidth: gridAreaWidth };
        }

        public setScroll() {
            let self = this,
                objCalulator = self.caculator(),
                height = objCalulator.gridAreaHeight > 466 ? 466 : objCalulator.gridAreaHeight,
                width = objCalulator.gridAreaWidth > 1200 ? 1200 : objCalulator.gridAreaWidth;
            $("#retirementDateSetting").igGrid("option", "height", height + 'px');
            $("#retirementDateSetting").igGrid("option", "width", width + 'px');

        }

        public bindHidingEvent() {
            let self = this;
            $("#retirementDateSetting").on("iggridhidingcolumnhiding", function (e, args) {
                self.hidedColumns.push(args.columnKey);
                console.log(self.hidedColumns);
            });
            $("#retirementDateSetting").on("iggridhidingcolumnshowing", function (e, args) {
                self.hidedColumns = self.hidedColumns.filter(v => v !== args.columnKey); 
                console.log(self.hidedColumns);
            });
        }

        /**
            * Export excel
            */
        public exportExcel(): void {
            let self = this;
            self.searchFilter.confirmCheckRetirementPeriod(true);
            let param = new ISearchParams(self.searchFilter);
            param.hidedColumns = self.hidedColumns;
            block.grayout();
            service.outPutFileExcel(param).done((data1) => {
                block.clear();
            })


        }
    }

}

