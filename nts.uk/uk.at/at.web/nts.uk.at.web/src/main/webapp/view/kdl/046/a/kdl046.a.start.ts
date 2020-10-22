module  nts.uk.at.view.kdl046.a {
    __viewContext.ready(function() {
        var screenModel = new viewmodel.ScreenModel();
        screenModel.startPage().done(function() {
            __viewContext.bind(screenModel);
            
            $("#switchButtonkdl046").focus();

            if (screenModel.target()) {
                setTimeout(function() {
                    let workplaceGroupList = screenModel.workplaceGroupList();
                    let item = _.filter(workplaceGroupList, function(o) { return o.id === screenModel.workplaceGroupId(); });
                    if (item.length() > 0) {
                        let codeSelected = item[0].code;
                        screenModel.currentCodes(codeSelected);
                        
                        
                        
                    } else {
                        

                    }

                }, 300);
            }
        });
    });
}