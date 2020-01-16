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

        constructor() {
            let self = this;
            self.searchFilter = new SearchFilterModel();
            self.plannedRetirements = ko.observableArray([]);
            self.searchFilter.retirementCourses = ko.observableArray([]);
            self.employeeInfo = ko.observable({});
            self.referEvaluationItems = ko.observableArray([]);
            $(".employee-info-pop-up").ntsPopup("hide");
            
            $(window).resize(function() {
                self.setScroll();
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

                let retirementAge = _.map(self.searchFilter.retirementCourses(), (rc) => {
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

            // let settingChanges = _.filter(self.plannedRetirements(), (p) => {
            //     return _.find($("#retirementDateSetting").ntsGrid("updatedCells"), { 'rowId': p.rKey })
            // });

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
                        if(changed[row].columnKey === 'desiredWorkingCourseId') {
                            let selectedPlan = _.filter(self.searchFilter.retirementCourses(), (plan) => {
                                return plan.retirePlanCourseId === changed[row].value;
                            });
                            if(selectedPlan) {
                                retire.desiredWorkingCourseId = selectedPlan.retirePlanCourseId;
                                retire.desiredWorkingCourseCd = selectedPlan.retirePlanCourseCode;
                                retire.desiredWorkingCourseName = selectedPlan.retirePlanCourseName;
                            }
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
                    if(retire.flag) {
                        retire.pendingFlag = 1;
                        retire.status = 0;
                    } else {
                        retire.pendingFlag = 0;
                        retire.status = 1;
                    }
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
                dataSource: self.searchFilter.retirementCourses(),
                dataSourceType: 'json',
                responseDataKey: 'results'
            });
        }

        public bindRetirementDateSettingGrid(): void {
            let self = this;
            if ($('#retirementDateSetting').data("igGrid")) {
                $('#retirementDateSetting').ntsGrid("destroy");
            };
            let comboColumns = [{ prop: 'retirePlanCourseName', length: 3 }];
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
            if(self.searchFilter.retirementCourses() && self.searchFilter.retirementCourses().length > 0) {
                fixedClmSetting.push({ columnKey: 'desiredWorkingCourseId', isFixed: true });
            }
            fixedClmSetting = fixedClmSetting.concat([
                { columnKey: 'employeeCode', isFixed: true },
                { columnKey: 'employeeName', isFixed: true }
            ]);
            let columns = self.buildRetirementDateSettingCol();
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
            console.log(columns);
            console.log(dataSources);
            console.log(sheets);
            console.log(fixedClmSetting);
            
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
                            // $('#retirementDateSetting').css('height', '425px');
                        }
                    },
                    { name: 'Paging', pageSize: 10, currentPageIndex: 0 },
                    { name: 'Resizing' },
                    { name: 'Hiding' },
                    // {
                    //     name: 'ColumnMoving', 
                    //     columnMovingDialogContainment: 'window',
                    // },
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
                    { name: 'WorkingCourseCb', width: '120px', options: _.sortBy(self.searchFilter.retirementCourses(), 'retirePlanCourseCode'), optionsValue: 'retirePlanCourseId', optionsText: 'retirePlanCourseName', columns: comboColumns, controlType: 'ComboBox', enable: true },
                    { name: 'EmployeeName', click: function (id, key, el) { self.showModal(id, key, el); }, controlType: 'LinkLabel' },
                    { name: 'InterviewRecord', click: function (id, key, el) { console.log(el); }, controlType: 'LinkLabel' },

                ]
            });
        }

        private buildRetirementDateSettingCol() {
            let self = this;
            let columns = [
                { headerText: 'key', key: 'rKey', dataType: 'string' },
                { headerText: getText('JCM008_A222_22'), key: 'flag', dataType: 'boolean', width: '70px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                { headerText: getText('JCM008_A222_23'), key: 'extendEmploymentFlg', dataType: 'number', width: '110px', ntsControl: 'RetirementStatusCb' },
                { headerText: getText('JCM008_A222_24'), key: 'registrationStatus', dataType: 'string', width: '110px', ntsControl: 'Label' }
            ];
            
            if (self.searchFilter.retirementCourses() && self.searchFilter.retirementCourses().length > 0) {
                columns.push({ headerText: getText('JCM008_A222_25'), key: 'desiredWorkingCourseId', dataType: 'number', width: '140px', ntsControl: 'WorkingCourseCb' });
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
                            hcGroups.push({ headerText: getText('JCM008_A222_36_' + (hc + 3)), key: 'hrEvaluation' + (hc + 1), dataType: 'string', width: '50px' , ntsControl: 'Label'});
                        }

                        columns.push(hcGroups.length > 1 ? {headerText: getText('JCM008_A222_36'), width: '80px', group: hcGroups} : { headerText: getText('JCM008_A222_36'), key: 'hrEvaluation1', dataType: 'string', width: '80px', ntsControl: 'Label' });
                    }
                    
                    if(item.evaluationItem == EvaluationItem.HEALTH_CONDITION && item.displayNum > 0 && item.usageFlg) {
                        let paGroups = [];
                        for (let pa = 0; pa < item.displayNum; pa ++) {
                            paGroups.push({ headerText: getText('JCM008_A222_37_' + (pa + 3)), key: 'healthStatus' + (pa + 1), dataType: 'string', width: '50px' , ntsControl: 'Label'});
                        }
                        columns.push(paGroups.length > 1 ? {headerText: getText('JCM008_A222_37'), width: '80px', group: paGroups} : { headerText: getText('JCM008_A222_37'), key: 'healthStatus1', dataType: 'string', width: '80px', ntsControl: 'Label' });
                    }
                    
                    if(item.evaluationItem == EvaluationItem.STRESS_CHECK && item.displayNum > 0 && item.usageFlg) {
                        let scGroups = [];
                        for (let sc = 0; sc < item.displayNum; sc ++) {
                            scGroups.push({ headerText: getText('JCM008_A222_38_' + (sc + 3)), key: 'stressStatus' + (sc + 1), dataType: 'string', width: '50px' , ntsControl: 'Label'});
                        }
                        columns.push( scGroups.length > 1 ? {headerText: getText('JCM008_A222_38'), width: '80px', group: scGroups} : { headerText: getText('JCM008_A222_38'), key: 'stressStatus1', dataType: 'string', width: '80px', ntsControl: 'Label'});
                    }
                });
            }

            columns.push({ headerText: getText('JCM008_A222_39'), key: 'interviewRecordTxt', dataType: 'string', width: '100px', ntsControl: 'InterviewRecord' });
            return columns;
        }

        public showModal(id, key, el) {
            // $('.nts-grid-control-' + key + '-' + id).append("<b>Appended text</b>");
            let self = this;
            let selectedEmp = _.findLast($("#retirementDateSetting").igGrid("option", "dataSource"), (retiredEmp) => {
                return retiredEmp.rKey === id;
            });

            let emp = {
                code: selectedEmp.employeeCode,
                name: selectedEmp.employeeName,
                kanaName: selectedEmp.businessnameKana,
                sex: selectedEmp.gender === 1 ? '男性' : '女性',
                dob: selectedEmp.birthday,
                age: selectedEmp.age + '歳',
                department: selectedEmp.departmentName,
                position: selectedEmp.position.positionName,
                employment: selectedEmp.employmentName,
                image: selectedEmp.avatarFile ? liveView(selectedEmp.avatarFile.facePhotoFileID) : 'images/avatar.png' 
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
            modal('hr', '/view/jdl/0110/a/index.xhtml').onClosed(function () {
                block.clear();
                let data = getShared('outputDepartmentJDL0110');
                self.searchFilter.department(data);
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
                let data = getShared('CDL002Output');
                self.searchFilter.employment(data);
            });
        }

        public caculator(): any {
                let contentArea = $("#contents-area")[0].getBoundingClientRect().height
                //height of combobox of display page size = 44
                let groupArea = 45 + $("#main-left-area")[0].getBoundingClientRect().height;
			return {contentAreaHeight: contentArea, gridAreaHeight: contentArea - groupArea};
        }

        public setScroll() {
            let self = this,
                objCalulator = self.caculator();
            $("#retirementDateSetting").igGrid("option", "height", objCalulator.gridAreaHeight + 'px');
            // $(".wrapScroll").css({ overflow: "auto", height: objCalulator.contentAreaHeight + "px" });
            // $("#grid_container").css("max-height", objCalulator.gridAreaHeight + 'px');

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

