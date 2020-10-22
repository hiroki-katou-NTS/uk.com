/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmm048.e {

  @bean()
  export class ViewModel extends ko.ViewModel {
    fileId: KnockoutObservable<string> = ko.observable('');

    created(params: string) {
      const vm = this;
      vm.fileId(params);
    }

    mounted() {
      const vm = this;
      $("#upload").ready(() => {
        $(".comfirm-checkbox").remove();
        $(`<button class='upload-webcam'> ${vm.$i18n('CMM048_107')} </button>`).insertAfter(".upload-btn");
      });
      if(vm.fileId()){
        $("#upload").ntsImageEditor("selectByFileId", vm.fileId());
      }
      
    }

    public closeDialog() {
      const vm = this;
      const fileId : string =  vm.fileId();
      vm.$window.close(fileId);
    }

    public upload() {
      let vm = this;
      if (nts.uk.ui.errors.hasError()) {
        return;
      }
      vm.$blockui('grayout');
      let isImageLoaded = $("#upload").ntsImageEditor("getImgStatus");
      if (isImageLoaded.imgOnView) {
        if ($("#upload").data("cropper") == undefined) {
          vm.closeDialog();
          return;
        }
        if ($("#upload").data("cropper").cropped) {
          $("#upload").ntsImageEditor("upload", { stereoType: "image" }).then((data: any) => {
            vm.fileId(data.id);
            vm.closeDialog();
            //vm.empFileMn().fileId = data.id;
          }).fail((error : any) => {
            vm.$blockui('clear')
            vm.$dialog.error(error);
          })
          .always(() => {
            vm.$blockui('clear');
          });
        } else {
          vm.closeDialog();
        }
      } else {
        vm.$dialog.error({ messageId: "Msg_617" });
        vm.$blockui('clear')
      }
    }
  }
}