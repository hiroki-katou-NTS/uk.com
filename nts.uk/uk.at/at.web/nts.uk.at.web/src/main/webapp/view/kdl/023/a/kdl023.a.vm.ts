module nts.uk.at.view.kdl023.a.viewmodel {

    import BaseScreenModel = kdl023.base.viewmodel.BaseScreenModel;

    @bean()
    export class ScreenModel extends BaseScreenModel {

        constructor(){
            super();
        }

        mounted(){
            const vm = this;
            vm.startPage().done(() => {

            });
        }
    }
}