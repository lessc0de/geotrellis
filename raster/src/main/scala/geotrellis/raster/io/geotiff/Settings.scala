/*
 * Copyright (c) 2014 Azavea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package geotrellis.raster.io.geotiff

/**
  * Used by geotiff.Encoder to control how GeoTiff data is to be written.
  *
  * Currently supports sample size, format and 'esriCompat', an ESRI
  * compatibility option.
  *
  * This compatibility option changes the way that geographic data are written.
  * The "normal" approach is similar to how GDAL and other strict GeoTIFF
  * encoders work (we write out the projected CS ID, i.e. 3857). If 'esriCompat'
  * is set to true, we instead write out a "user-defined" projected CS.
  */
case class Settings(size: SampleSize, format: SampleFormat,
  compression: Compression, nodata: Nodata = Nodata.Default) {
  def setSize(s: SampleSize) = Settings(s, format, compression)
  def setFormat(s: SampleFormat) = Settings(size, s, compression)
  def setCompression(c: Compression) = Settings(size, format, c)
  def setNodata(d: Double) = Settings(size, format, compression, Nodata(d, true))

  def nodataInt = nodata.toInt(this)
  def nodataString = nodata.toString(this)
}

object Settings {
  def uint8 = Settings(ByteSample, Unsigned, Uncompressed)
  def uint16 = Settings(ShortSample, Unsigned, Uncompressed)
  def uint32 = Settings(IntSample, Unsigned, Uncompressed)

  def int8 = Settings(ByteSample, Signed, Uncompressed)
  def int16 = Settings(ShortSample, Signed, Uncompressed)
  def int32 = Settings(IntSample, Signed, Uncompressed)

  def float32 = Settings(IntSample, Floating, Uncompressed)
  def float64 = Settings(LongSample, Floating, Uncompressed)
}
