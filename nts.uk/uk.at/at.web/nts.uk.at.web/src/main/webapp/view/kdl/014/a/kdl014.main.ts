__viewContext.ready(function() {
    __viewContext.bind({
        openKDL014() {
            nts.uk.ui.windows.sub.modal('/view/kdl/014/a/index.xhtml', { title: '打刻参照', });
            nts.uk.ui.windows.setShared("kdl014startDateA", $("#startDateA").val());
            nts.uk.ui.windows.setShared("kdl014endDateA", $("#endDateA").val());
            nts.uk.ui.windows.setShared("kdl014employeeCodeA", $("#employeeCodeA").val());
        };
        openKDL014B() {
            nts.uk.ui.windows.sub.modal('/view/kdl/014/b/index.xhtml', { title: '打刻参照B', });
            nts.uk.ui.windows.setShared("kdl014startDateB", $("#startDateB").val());
            nts.uk.ui.windows.setShared("kdl014endDateB", $("#endDateB").val());
            nts.uk.ui.windows.setShared("kdl014lstEmployeeB", $("#lstEmployeeB").val());
        }
    });
}