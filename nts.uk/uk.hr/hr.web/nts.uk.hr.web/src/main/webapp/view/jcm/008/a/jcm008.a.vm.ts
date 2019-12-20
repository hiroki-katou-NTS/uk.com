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
        retirementSettings: KnockoutObservable<Array<RetirementSetting>>;
        selectedRetirementSetting: KnockoutObservable<string>;
        selectedRetirementEmployee: KnockoutObservable<any>;
        retirementDateSetting: KnockoutObservable<Array<any>>;
        employeeInfo: KnockoutObservable<IEmployee>;
        constructor() {
            let self = this;
            self.searchFilter = new SearchFilterModel();
            self.retirementSettings = ko.observableArray([
                new RetirementSetting('1', '基本給'),
                new RetirementSetting('2', '役職手当'),
                new RetirementSetting('3', '基本給ながい文字列ながい文字列ながい文字列')
            ]);
            self.retirementDateSetting = [
                { employeeCode: 'S100001', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100002', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100003', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100004', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100005', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100006', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100007', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100008', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100009', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100010', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false },
                { employeeCode: 'S100011', employeeName: '社員名', employeeKanaName: '社員名', employeeDob: '1954/11/11', employeeAge: '65分', department: '局売り場', employment: '雇用', hireDate: '1954/11/11', retirementDate: '1954/11/11', releaseDate: '1954/11/11', inputDate: '1954/11/11', retirementStatus: '1', registrationStatus: '登録状況', desiredWorkingCourse: '1', personnelAssessment1: 'A', personnelAssessment2: 'B', personnelAssessment3: 'C', healthCondition1: 'A', healthCondition2: 'A', healthCondition3: 'A', stressCheck1: 'A', stressCheck2: 'A', stressCheck3: 'B', interviewRecord: getText('JCM008_A222_57'), flag: false }
            ];
            self.bindRetirementAgeGrid();
            self.bindRetirementDateSettingGrid();
            self.selectedRetirementSetting = self.retirementSettings()[0].code;
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
                dfd.resolve();
                // block.clear();
            }).fail((error) => {
                console.log('Get Data Fail');
                dfd.reject();
                nts.uk.ui.dialog.info(error);
            }).always(() => {
                block.clear();
            });
            
            return dfd.promise();
        }

        /** event when click register */
        getRetirementData(force) {
            let self = this;
            let param = new ISearchParams(self.searchFilter);
            if(force instanceof Boolean) {
                param.confirmCheckRetirementPeriod = force;
            }
            block.grayout();
            let dfd = $.Deferred<any>();
            service.searchRetireData(param)
                .done((res) => {
                    console.log(res);
                })
                .fail((error) => {
                    dfd.reject();
                    console.log(error);
                    if(error.messageId == "MsgJ_JCM008_5") {
                        nts.uk.ui.dialog.confirm({ messageId: "MsgJ_JCM008_5" }).ifYes(() => {
                            self.getRetirementData(true);
                        });
                    } else {
                        nts.uk.ui.dialog.info(error);
                    }
                })
                .always(() => {
                    block.clear();
                })
        }

        public bindRetirementAgeGrid(): void {
            $('#retirementAgeInfo').ntsGrid({
                autoGenerateColumns: false,
                columns: [
                    { headerText: getText('JCM008_A222_13'), key: 'classification', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_14'), key: 'age', dataType: 'string', width: '70px' },
                    { headerText: getText('JCM008_A222_15'), key: 'standard', dataType: 'string', width: '220px' },

                ],
                dataSource: [
                    { classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準' },
                    { classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準' },
                    { classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準' },
                    { classification: '雇用区分', age: '65分', standard: '退職日基準 退職日基準' }
                ],
                dataSourceType: 'json',
                responseDataKey: 'results'
            });
        }

        public bindRetirementDateSettingGrid(): void {
            let self = this;
            let comboColumns = [{ prop: 'name', length: 2 }];
            $('#retirementDateSetting').ntsGrid({
                autoGenerateColumns: false,
                width: '1200px',
                height: '500px',
                primaryKey: 'employeeCode',
                rowVirtualization: true,
                virtualization: true,
                virtualizationMode: 'continuous',
                hidePrimaryKey: false,
                // autoFitWindow: true,
                columns: [
                    { headerText: getText('JCM008_A222_22'), key: 'flag', dataType: 'boolean', width: '70px', showHeaderCheckbox: true, ntsControl: 'Checkbox' },
                    { headerText: getText('JCM008_A222_23'), key: 'retirementStatus', dataType: 'string', width: '80px', ntsControl: 'RetirementStatusCb' },
                    { headerText: getText('JCM008_A222_24'), key: 'registrationStatus', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_25'), key: 'desiredWorkingCourse', dataType: 'string', width: '100px', ntsControl: 'DesiredWorkingCourseCb' },
                    { headerText: getText('JCM008_A222_26'), key: 'employeeCode', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_27'), key: 'employeeName', dataType: 'string', width: '100px', ntsControl: 'EmployeeName' },
                    { headerText: getText('JCM008_A222_28'), key: 'employeeKanaName', dataType: 'string', width: '100px' },
                    { headerText: getText('JCM008_A222_29'), key: 'employeeDob', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_30'), key: 'employeeAge', dataType: 'string', width: '80px' },
                    { headerText: getText('JCM008_A222_31'), key: 'department', dataType: 'string', width: '90px' },
                    { headerText: getText('JCM008_A222_32'), key: 'employment', dataType: 'string', width: '90px' },
                    { headerText: getText('JCM008_A222_33'), key: 'hireDate', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_34'), key: 'retirementDate', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_35'), key: 'releaseDate', dataType: 'string', width: '95px' },
                    { headerText: getText('JCM008_A222_35_1'), key: 'inputDate', dataType: 'string', width: '95px' },
                    {
                        headerText: getText('JCM008_A222_36'),
                        group: [
                            { headerText: '', key: 'personnelAssessment1', dataType: 'string', width: '40px' },
                            { headerText: '', key: 'personnelAssessment2', dataType: 'string', width: '40px' },
                            { headerText: '', key: 'personnelAssessment3', dataType: 'string', width: '40px' }
                        ]
                    },
                    {
                        headerText: getText('JCM008_A222_37'),
                        group: [
                            { headerText: '', key: 'healthCondition1', dataType: 'string', width: '40px' },
                            { headerText: '', key: 'healthCondition2', dataType: 'string', width: '40px' },
                            { headerText: '', key: 'healthCondition3', dataType: 'string', width: '40px' }
                        ]
                    }, 
                    {
                        headerText: getText('JCM008_A222_38'),
                        group: [
                            { headerText: '', key: 'stressCheck1', dataType: 'string', width: '40px' },
                            { headerText: '', key: 'stressCheck2', dataType: 'string', width: '40px' },
                            { headerText: '', key: 'stressCheck3', dataType: 'string', width: '40px' }
                        ]
                    },
                    { headerText: getText('JCM008_A222_39'), key: 'interviewRecord', dataType: 'string', width: '100px', ntsControl: 'InterviewRecord' },
                ],
                dataSource: self.retirementDateSetting,
                dataSourceType: 'json',
                responseDataKey: 'results',
                tabIndex: 11,
                features: [
                    {
                        name: "Selection",mode: "row",multipleSelection: true,
                        rowSelectionChanged: function (evt, ui) {
                            let itemSelected = _.find(self.selectedRetirementEmployee, function (o) { return o.employeeCode == ui.row.employeeCode; });
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
                    {name: 'Paging',pageSize: 10,currentPageIndex: 0},
                    {name: 'Resizing'},
                    {name: 'MultiColumnHeaders'}
                ],
                ntsControls: [
                    { name: 'Checkbox', options: { value: 1, text: '' }, optionsValue: 'value', optionsText: 'text', controlType: 'CheckBox', enable: true },
                    { name: 'RetirementStatusCb', width: '75px', options: [new ItemModel('1', '退職'), new ItemModel('2', '継続')], optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
                    { name: 'DesiredWorkingCourseCb', width: '80px', options: [new ItemModel('1', '希望勤務'), new ItemModel('2', 'コース')], optionsValue: 'code', optionsText: 'name', columns: comboColumns, controlType: 'ComboBox', enable: true },
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
    }

}

