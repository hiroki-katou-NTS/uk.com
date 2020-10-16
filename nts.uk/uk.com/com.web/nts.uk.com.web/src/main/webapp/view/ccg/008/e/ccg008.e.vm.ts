/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.at.view.kdl048.screenModel {
  @bean()
  export class ViewModel extends ko.ViewModel {
    created(){

    }
    mounted(){

    }
    onClickDecision(){

    }
    onClickCancel(){
      this.$window.close()
    }
  }
}