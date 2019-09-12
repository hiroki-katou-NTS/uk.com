<template>
<div class="kdws03e">
    <div class="modal-header uk-bg-white-smoke pl-0 pr-0 rounded-0">
    <div class="row uk-text-quote w-100 ml-0 mr-0" v-on:click="$close()">
      <div class="col-1 pl-1"><i class="fas fa-arrow-left"></i></div>
      <div class="col-6 pl-0 pr-2"><span>{{ params.empName }}</span></div>
      <div class="col-5 pl-0 pr-0"><span>{{ params.date  | date('YYYY年MM月DD日')}}</span></div>
    </div>
  </div>
    <div>{{'KDWS03_43' | i18n}}</div>
    <div class="row pt-2">
        <div class="col-4">{{'KDWS03_42' | i18n}}{{'KDWS03_46' | i18n}}</div>
        <div class="col-8">{{errorInfo.code}}</div>
    </div>
    <div class="row pt-2">
        <div class="col-4">{{'KDWS03_43' | i18n}}{{'KDWS03_46' | i18n}}</div>
        <div class="col-8">{{errorInfo.name}}</div>
    </div>
    <div class="row pt-2">
        <div class="col-4">{{'KDWS03_44' | i18n}}{{'KDWS03_46' | i18n}}</div>
        <div class="col-8">{{errorInfo.errMsg}}</div>
    </div>
    <div class="fixed-bottom" v-if="displayE71">
      <button class = 'btn uk-bg-process btn-block modal-dialog dropback'  >{{'KDWS03_72' | i18n}}</button>
    </div>
</div>
</template>