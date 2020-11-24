/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />
module nts.uk.com.view.cmm048.e {

  @bean()
  export class ViewModel extends ko.ViewModel {
    fileId: KnockoutObservable<string> = ko.observable('');
    showEditAction : KnockoutObservable<boolean> = ko.observable(false);
    created(params: string) {
      const vm = this;
      vm.fileId(params);
    }

    mounted() {
      const vm = this;
      $("#upload").ready(() => {
        $(".comfirm-checkbox").remove();
        $(".edit-action-container").hide();
        $(`<button data-bind="click: openDialogE2"> ${vm.$i18n('CMM048_107')} </button>`)
          .attr('class', 'upload-webcam')
          .insertAfter(".upload-btn");
        ko.applyBindings(vm, $(".upload-webcam")[0]);
      });
      if (vm.fileId()) {
        $(".edit-action-container").show();
        $("#upload").ntsImageEditor("selectByFileId", vm.fileId());
      }
    }

    public openDialogE2() {
      const vm = this;
      //TODO
      vm.$window.modal("/view/cmm/048/f/index.xhtml").then((uri: string) => {
        if (uri) {
          $(".edit-action-container").show();
          $("#upload").ntsImageEditor("showByUrl", { url: uri });
        }
      });
    }

    public closeDialog() {
      const vm = this;
      const fileId: string = vm.fileId();
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
        $(".edit-action-container").show();
        if ($("#upload").data("cropper") == undefined) {
          vm.closeDialog();
          return;
        }
        if ($("#upload").data("cropper").cropped) {
          $("#upload").ntsImageEditor("upload", { stereoType: "image" }).then((data: any) => {
            vm.fileId(data.id);
            vm.closeDialog();
          }).fail((error: any) => {
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