<template>
<div class="documentscontrolssearch-box">
    <h5>1. {{'sample' | i18n}}</h5>
    <time-range-search-box v-on:search="searchList" />

    <markdown />

    <br>
    <label>
      Tạo bởi:
      <b>Phạm Văn Dân</b>
    </label>
    <br>
</div>
</template>
