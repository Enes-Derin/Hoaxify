import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import en from "./translations/en.json"
import tr from "./translations/tr.json"

    const initiallanguage= localStorage.getItem('lang') || navigator.language || "en"

    export const i18nInstance = i18n.use(initReactI18next)

i18nInstance 
  .init({
  resources: {
      en: {
        translation : en 
      },
      tr: {
        translation : tr
      }
    },// if you're using a language detector, do not define the lng option
    fallbackLng: initiallanguage ,

    interpolation: {
      escapeValue: false // react already safes from xss => https://www.i18next.com/translation-function/interpolation#unescape
    }
  });