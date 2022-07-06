package model;

/**
 * Interface supporting downscaling and applying image mosaic.
 */
public interface IMosaicDownscaleModel extends IModel {

  /**
   * Applies a mosaic transformation on an image.
   * @param numSeeds the number of seeds the mosaic has.
   */
  void mosaic(int numSeeds);

  /**
   * Applies a downscaling effect to an image.
   * @param ratio the ratio of the downscaling.
   */
  void downscale(double ratio);

}
