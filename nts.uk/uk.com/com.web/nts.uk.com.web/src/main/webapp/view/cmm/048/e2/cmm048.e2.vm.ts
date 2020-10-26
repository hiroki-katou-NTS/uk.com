/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmm048.e2 {

  @bean()
  export class ViewModel extends ko.ViewModel {
   
    snapYet : KnockoutObservable<boolean> = ko.observable(false);
    avatarUri : KnockoutObservable<string> = ko.observable('');
    mounted() {
      const vm = this;
    }

    public snap() {
      // take snapshot and get image data
      const vm = this;
      Webcam.snap((uri : any) => {
        // display results in page
        vm.avatarUri(uri);
        console.log(uri)
        $('#avatar-zone').html('');
        $('#avatar-zone').append('<img src="'+uri+'"/>'); 
      } );
      vm.snapYet(true);
    }

    public snapAgain() {
      const vm = this;
      vm.snapYet(false);
      Webcam.attach('#avatar-zone');
    }

    public takeThis() {
      const vm = this;
      vm.$window.close(vm.avatarUri());
    }

    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }
  }
}