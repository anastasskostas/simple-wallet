export const ConvertMillisecondsToDate = (milliseconds) => {
  if (milliseconds < 0) {
    return null;
  }
  const date = new Date(milliseconds);
  const convertedDate = date.getFullYear() + "-" + addZero(date.getMonth() + 1) + "-" + addZero(date.getDate()) + " " + addZero(date.getHours()) + ":" + addZero(date.getMinutes()) + ":" + addZero(date.getSeconds());
  return convertedDate;
}

const addZero = (value) => {
  return (value < 10 ? '0' : '') + value;
}
