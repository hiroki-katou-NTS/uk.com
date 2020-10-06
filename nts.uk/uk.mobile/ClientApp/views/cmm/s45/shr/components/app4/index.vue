<template>
  <div class="cmms45componentsapp4">
    <!-- B2 -->
    <div class="card card-label">
      <div class="card-header uk-bg-accordion headerDiv">
        <span class="textPosition">{{'KAFS07_2' | i18n}}</span>
      </div>
    </div>
    <!-- B2_1 -->
    <div v-if="isCondition1" class="paddingSpan">
      <span v-if="dataOutput.goBackApplication.isChangedWork == 1">変更する</span>
    </div>
    <!-- B3 -->

    <div class="card card-label">
      <div class="card-header uk-bg-accordion headerDiv">
        <span class="textPosition">{{'KAFS09_3' | i18n}}</span>
      </div>
    </div>
    <!-- B3_1 -->
    <div>
      <span class="textSize uk-text-dark-gray">{{'KAFS09_04' | i18n}}</span>
    </div>
    <!-- B3_2 -->
    <div class="paddingSpan">
      <span>{{$app().workType | i18n}}</span>
    </div>
    <!-- B3_3 -->
    <div>
      <span class="textSize uk-text-dark-gray">{{'KAFS09_05' | i18n}}</span>
    </div>
    <!-- B3_4 -->
    <div class="paddingSpan">
      <span>{{$app().workTime | i18n}}</span>
    </div>

    <!-- B5 -->

    <div v-if="isCondition1" class="card card-label">
      <div class="card-header uk-bg-accordion headerDiv">
        <span class="textPosition">{{'KAFS09_08' | i18n}}</span>
      </div>
    </div>
    <!-- B5_1 -->
    <div v-if="isCondition1" class="paddingSpan">
      <span v-if="$app().straight">{{'KAFS09_06' | i18n}}</span>
      <span v-if="!$app().straight">{{'KAFS09_07' | i18n}}</span>
    </div>
    <!-- B5_2 -->
    <div v-if="isCondition1" class="paddingSpan">
      <span v-if="$app().bounce">{{'KAFS09_09' | i18n}}</span>
      <span v-if="!$app().bounce">{{'KAFS09_10' | i18n}}</span>
    </div>
  </div>
</template>