module nts.uk.at.view.ksc001.g {
    __viewContext.ready(function() {
        let screenModel = new nts.uk.at.view.ksc001.g.viewmodel.ScreenModel();
        
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);

            $("#gridTable").ntsGrid({
                width: null,
                height: '400px',
                dataSource: screenModel.items(),
                primaryKey: 'id',
                virtualization: true,
                virtualizationMode: 'continuous',
                columns: [
                    { headerText: '', key: 'id', dataType: 'string', width: '150px',hidden: true },
                    { headerText: nts.uk.resource.getText('KSC001_66'), key: 'exeDay', dataType: 'string', width: '100px' },
                    { headerText: nts.uk.resource.getText('KSC001_67'), key: 'exeEmployeeCode', dataType: 'string', width: '150px' },
                    { headerText: nts.uk.resource.getText('KSC001_68'), key: 'exeEmployeeName', dataType: 'string', width: '200px' },
                    { headerText: nts.uk.resource.getText('KSC001_31'), key: 'targetPeriod', dataType: 'string', width: '220px' },
                    { headerText: nts.uk.resource.getText('KSC001_69'), key: 'status', dataType: 'string', width: '150px' },
                    { headerText: nts.uk.resource.getText('KSC001_70'), key: 'exeId', dataType: 'string', width: '80px', unbound: true, ntsControl: 'Button' }
                ],
                features: [],
                ntsFeatures: [{ name: 'CopyPaste' }],
                ntsControls: [
                    {
                        name: 'Button', text: nts.uk.resource.getText('KSC001_71'), click: function() {
                            nts.uk.ui.windows.sub.modal("/view/ksc/001/h/index.xhtml").onClosed(() => {
                            });
                        },
                        controlType: 'Button' }
                ]
            });
            $("#fixed-table").ntsFixedTable({ height: 430 });
        });
    });
}
