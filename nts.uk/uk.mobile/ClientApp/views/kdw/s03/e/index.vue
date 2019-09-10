<template>
<div class="kdws03e">
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
      <button class = 'btn uk-bg-process btn-block'  >{{'KDWS03_72' | i18n}}</button>
    </div>
</div>
</template>