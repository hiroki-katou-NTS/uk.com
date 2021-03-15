module nts.uk.at.view.kdl049.a {
	let dataShare = nts.uk.ui.windows.getShared('KDL049');
	let targetWorkplace = null;
	if (dataShare.workplace != null && dataShare.workplace.workPlaceID != 0) {
       	targetWorkplace = dataShare.workplace.targetOrgWorkplaceName;
        }
            if (targetWorkplace != null) {
                $('head').append("<script>var dialogSize = { width: 350, height: 370 };<\/script>");
            } else {
                $('head').append("<script>var dialogSize = { width: 350, height: 300 };<\/script>");
            }
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
             if(__viewContext.user.role.isInCharge.attendance == true){
                
                 $("#A22").focus();
                }else{
                  $("#A24").focus();
                }
        });
       
    });
}