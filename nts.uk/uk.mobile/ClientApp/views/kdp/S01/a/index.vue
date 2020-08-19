<template>
  <div class="mx-n3">
    <div
      class="pl-3 pt-2 pr-2"
      v-bind:style="{
        'background-color':
          setting.displaySettingsStampScreen.settingDateTimeColor
            .backgroundColor,
        color: setting.displaySettingsStampScreen.settingDateTimeColor.textColor
      }"
    >
      <div class="font-weight-bold clearfix">
        <span id="date">
          {{ $dt.now | date("YYYY年 MM月 DD日（ddd）") }}
        </span>
        <button
          v-on:click="openDialogS"
          type="button"
          class="float-right btn btn-success"
        >
          {{ "KDPS01_25" | i18n }}
        </button>
      </div>
      <div class="pl-5 font-weight-bold">
        <span id="time">{{ $dt.now | date("HH:mm") }}</span>
      </div>
    </div>

    <div
      class="p-2 text-break"
      v-bind:style="{
        color: setting.stampPageComment.commentColor
      }"
      v-html="textComment"
    ></div>
    <div class="px-3">
      <div class="row">
        <div
          class="col-6 mb-2 px-2 div-btn-stamp"
          v-for="button in setting.buttons"
          v-bind:key="button.buttonPositionNo"
        >
          <button
            type="button"
            v-if="button.buttonDisSet.displayBackGroundColor !== ''"
            v-bind:style="{
              color: button.buttonDisSet.buttonNameSet.textColor,
              'background-color': button.buttonDisSet.displayBackGroundColor
            }"
            v-click:500="() => stampClick(button)"
            class="col-12 btn-stamp btn btn-secondary "
          >
            {{ button.buttonDisSet.buttonNameSet.buttonName }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
